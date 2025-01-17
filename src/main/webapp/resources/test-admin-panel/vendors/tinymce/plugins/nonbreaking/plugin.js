(function () {
  var defs = {}; // id -> {dependencies, definition, instance (possibly undefined)}

  // Used when there is no 'main' module.
  // The name is probably (hopefully) unique so minification removes for releases.
  var register_3795 = function (id) {
    var module = dem(id);
    var fragments = id.split(".");
    var target = Function("return this;")();
    for (var i = 0; i < fragments.length - 1; ++i) {
      if (target[fragments[i]] === undefined) target[fragments[i]] = {};
      target = target[fragments[i]];
    }
    target[fragments[fragments.length - 1]] = module;
  };

  var instantiate = function (id) {
    var actual = defs[id];
    var dependencies = actual.deps;
    var definition = actual.defn;
    var len = dependencies.length;
    var instances = new Array(len);
    for (var i = 0; i < len; ++i) instances[i] = dem(dependencies[i]);
    var defResult = definition.apply(null, instances);
    if (defResult === undefined) throw "module [" + id + "] returned undefined";
    actual.instance = defResult;
  };

  var def = function (id, dependencies, definition) {
    if (typeof id !== "string") throw "module id must be a string";
    else if (dependencies === undefined) throw "no dependencies for " + id;
    else if (definition === undefined) throw "no definition function for " + id;
    defs[id] = {
      deps: dependencies,
      defn: definition,
      instance: undefined,
    };
  };

  var dem = function (id) {
    var actual = defs[id];
    if (actual === undefined) throw "module [" + id + "] was undefined";
    else if (actual.instance === undefined) instantiate(id);
    return actual.instance;
  };

  var req = function (ids, callback) {
    var len = ids.length;
    var instances = new Array(len);
    for (var i = 0; i < len; ++i) instances.push(dem(ids[i]));
    callback.apply(null, callback);
  };

  var ephox = {};

  ephox.bolt = {
    module: {
      api: {
        define: def,
        require: req,
        demand: dem,
      },
    },
  };

  var define = def;
  var require = req;
  var demand = dem;
  // this helps with minificiation when using a lot of global references
  var defineGlobal = function (id, ref) {
    define(id, [], function () {
      return ref;
    });
  };
  /*jsc
["tinymce.plugins.nonbreaking.Plugin","tinymce.core.PluginManager","global!tinymce.util.Tools.resolve"]
jsc*/
  defineGlobal("global!tinymce.util.Tools.resolve", tinymce.util.Tools.resolve);
  /**
   * ResolveGlobal.js
   *
   * Released under LGPL License.
   * Copyright (c) 1999-2017 Ephox Corp. All rights reserved
   *
   * License: http://www.tinymce.com/license
   * Contributing: http://www.tinymce.com/contributing
   */

  define("tinymce.core.PluginManager", [
    "global!tinymce.util.Tools.resolve",
  ], function (resolve) {
    return resolve("tinymce.PluginManager");
  });

  /**
   * Plugin.js
   *
   * Released under LGPL License.
   * Copyright (c) 1999-2017 Ephox Corp. All rights reserved
   *
   * License: http://www.tinymce.com/license
   * Contributing: http://www.tinymce.com/contributing
   */

  /**
   * This class contains all core logic for the nonbreaking plugin.
   *
   * @class tinymce.nonbreaking.Plugin
   * @private
   */
  define("tinymce.plugins.nonbreaking.Plugin", [
    "tinymce.core.PluginManager",
  ], function (PluginManager) {
    PluginManager.add("nonbreaking", function (editor) {
      var setting = editor.getParam("nonbreaking_force_tab");

      editor.addCommand("mceNonBreaking", function () {
        editor.insertContent(
          editor.plugins.visualchars && editor.plugins.visualchars.state
            ? '<span class="mce-nbsp">&nbsp;</span>'
            : "&nbsp;",
        );

        editor.dom.setAttrib(
          editor.dom.select("span.mce-nbsp"),
          "data-mce-bogus",
          "1",
        );
      });

      editor.addButton("nonbreaking", {
        title: "Nonbreaking space",
        cmd: "mceNonBreaking",
      });

      editor.addMenuItem("nonbreaking", {
        text: "Nonbreaking space",
        cmd: "mceNonBreaking",
        context: "insert",
      });

      if (setting) {
        var spaces = +setting > 1 ? +setting : 3; // defaults to 3 spaces if setting is true (or 1)

        editor.on("keydown", function (e) {
          if (e.keyCode == 9) {
            if (e.shiftKey) {
              return;
            }

            e.preventDefault();
            for (var i = 0; i < spaces; i++) {
              editor.execCommand("mceNonBreaking");
            }
          }
        });
      }
    });

    return function () {};
  });
  dem("tinymce.plugins.nonbreaking.Plugin")();
})();
