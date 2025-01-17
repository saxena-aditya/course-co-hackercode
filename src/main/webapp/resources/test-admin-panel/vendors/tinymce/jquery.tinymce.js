!(function () {
  var a = {},
    b = function (b) {
      for (
        var c = a[b],
          e = c.deps,
          f = c.defn,
          g = e.length,
          h = new Array(g),
          i = 0;
        i < g;
        ++i
      )
        h[i] = d(e[i]);
      var j = f.apply(null, h);
      if (void 0 === j) throw "module [" + b + "] returned undefined";
      c.instance = j;
    },
    c = function (b, c, d) {
      if ("string" != typeof b) throw "module id must be a string";
      if (void 0 === c) throw "no dependencies for " + b;
      if (void 0 === d) throw "no definition function for " + b;
      a[b] = { deps: c, defn: d, instance: void 0 };
    },
    d = function (c) {
      var d = a[c];
      if (void 0 === d) throw "module [" + c + "] was undefined";
      return void 0 === d.instance && b(c), d.instance;
    },
    e = function (a, b) {
      for (var c = a.length, e = new Array(c), f = 0; f < c; ++f)
        e.push(d(a[f]));
      b.apply(null, b);
    },
    f = {};
  f.bolt = { module: { api: { define: c, require: e, demand: d } } };
  var g = c;
  g("0", [], function () {
    return function (a) {
      function b() {
        function a(a) {
          "remove" === a &&
            this.each(function (a, b) {
              var c = d(b);
              c && c.remove();
            }),
            this.find("span.mceEditor,div.mceEditor").each(function (a, b) {
              var c = i().get(b.id.replace(/_parent$/, ""));
              c && c.remove();
            });
        }
        function b(b) {
          var c,
            d = this;
          if (null != b)
            a.call(d),
              d.each(function (a, c) {
                var d;
                (d = i().get(c.id)) && d.setContent(b);
              });
          else if (d.length > 0 && (c = i().get(d[0].id)))
            return c.getContent();
        }
        function d(a) {
          var b = null;
          return a && a.id && g.tinymce && (b = i().get(a.id)), b;
        }
        function e(a) {
          return !!(a && a.length && g.tinymce && a.is(":tinymce"));
        }
        var h = {};
        f.each(["text", "html", "val"], function (a, g) {
          var i = (h[g] = f.fn[g]),
            j = "text" === g;
          f.fn[g] = function (a) {
            var g = this;
            if (!e(g)) return i.apply(g, arguments);
            if (a !== c)
              return (
                b.call(g.filter(":tinymce"), a),
                i.apply(g.not(":tinymce"), arguments),
                g
              );
            var h = "",
              k = arguments;
            return (
              (j ? g : g.eq(0)).each(function (a, b) {
                var c = d(b);
                h += c
                  ? j
                    ? c
                        .getContent()
                        .replace(/<(?:"[^"]*"|'[^']*'|[^'">])*>/g, "")
                    : c.getContent({ save: !0 })
                  : i.apply(f(b), k);
              }),
              h
            );
          };
        }),
          f.each(["append", "prepend"], function (a, b) {
            var g = (h[b] = f.fn[b]),
              i = "prepend" === b;
            f.fn[b] = function (a) {
              var b = this;
              return e(b)
                ? a !== c
                  ? ("string" == typeof a &&
                      b.filter(":tinymce").each(function (b, c) {
                        var e = d(c);
                        e &&
                          e.setContent(
                            i ? a + e.getContent() : e.getContent() + a,
                          );
                      }),
                    g.apply(b.not(":tinymce"), arguments),
                    b)
                  : void 0
                : g.apply(b, arguments);
            };
          }),
          f.each(
            ["remove", "replaceWith", "replaceAll", "empty"],
            function (b, c) {
              var d = (h[c] = f.fn[c]);
              f.fn[c] = function () {
                return a.call(this, c), d.apply(this, arguments);
              };
            },
          ),
          (h.attr = f.fn.attr),
          (f.fn.attr = function (a, g) {
            var i = this,
              j = arguments;
            if (!a || "value" !== a || !e(i))
              return g !== c ? h.attr.apply(i, j) : h.attr.apply(i, j);
            if (g !== c)
              return (
                b.call(i.filter(":tinymce"), g),
                h.attr.apply(i.not(":tinymce"), j),
                i
              );
            var k = i[0],
              l = d(k);
            return l ? l.getContent({ save: !0 }) : h.attr.apply(f(k), j);
          });
      }
      var c,
        d,
        e,
        f,
        g,
        h = [];
      (g = a ? a : window), (f = g.jQuery);
      var i = function () {
        return g.tinymce;
      };
      (f.fn.tinymce = function (a) {
        function c() {
          var c = [],
            d = 0;
          e || (b(), (e = !0)),
            m.each(function (b, e) {
              var f,
                g = e.id,
                h = a.oninit;
              g || (e.id = g = i().DOM.uniqueId()),
                i().get(g) ||
                  ((f = i().createEditor(g, a)),
                  c.push(f),
                  f.on("init", function () {
                    var a,
                      b = h;
                    m.css("visibility", ""),
                      h &&
                        ++d == c.length &&
                        ("string" == typeof b &&
                          ((a =
                            b.indexOf(".") === -1
                              ? null
                              : i().resolve(b.replace(/\.\w+$/, ""))),
                          (b = i().resolve(b))),
                        b.apply(a || i(), c));
                  }));
            }),
            f.each(c, function (a, b) {
              b.render();
            });
        }
        var j,
          k,
          l,
          m = this,
          n = "";
        if (!m.length) return m;
        if (!a) return i() ? i().get(m[0].id) : null;
        if (
          (m.css("visibility", "hidden"), g.tinymce || d || !(j = a.script_url))
        )
          1 === d ? h.push(c) : c();
        else {
          (d = 1),
            (k = j.substring(0, j.lastIndexOf("/"))),
            j.indexOf(".min") != -1 && (n = ".min"),
            (g.tinymce = g.tinyMCEPreInit || { base: k, suffix: n }),
            j.indexOf("gzip") != -1 &&
              ((l = a.language || "en"),
              (j =
                j +
                (/\?/.test(j) ? "&" : "?") +
                "js=true&core=true&suffix=" +
                escape(n) +
                "&themes=" +
                escape(a.theme || "modern") +
                "&plugins=" +
                escape(a.plugins || "") +
                "&languages=" +
                (l || "")),
              g.tinyMCE_GZ ||
                (g.tinyMCE_GZ = {
                  start: function () {
                    function b(a) {
                      i().ScriptLoader.markDone(i().baseURI.toAbsolute(a));
                    }
                    b("langs/" + l + ".js"),
                      b("themes/" + a.theme + "/theme" + n + ".js"),
                      b("themes/" + a.theme + "/langs/" + l + ".js"),
                      f.each(a.plugins.split(","), function (a, c) {
                        c &&
                          (b("plugins/" + c + "/plugin" + n + ".js"),
                          b("plugins/" + c + "/langs/" + l + ".js"));
                      });
                  },
                  end: function () {},
                }));
          var o = document.createElement("script");
          (o.type = "text/javascript"),
            (o.onload = o.onreadystatechange =
              function (b) {
                (b = b || window.event),
                  2 === d ||
                    ("load" != b.type &&
                      !/complete|loaded/.test(o.readyState)) ||
                    ((i().dom.Event.domLoaded = 1),
                    (d = 2),
                    a.script_loaded && a.script_loaded(),
                    c(),
                    f.each(h, function (a, b) {
                      b();
                    }));
              }),
            (o.src = j),
            document.body.appendChild(o);
        }
        return m;
      }),
        f.extend(f.expr[":"], {
          tinymce: function (a) {
            var b;
            return !!(
              a.id &&
              "tinymce" in g &&
              ((b = i().get(a.id)), b && b.editorManager === i())
            );
          },
        });
    };
  }),
    d("0")();
})();
