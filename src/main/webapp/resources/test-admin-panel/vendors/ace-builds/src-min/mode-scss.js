define(
  "ace/mode/scss_highlight_rules",
  [
    "require",
    "exports",
    "module",
    "ace/lib/oop",
    "ace/lib/lang",
    "ace/mode/text_highlight_rules",
  ],
  function (e, t, n) {
    "use strict";
    var r = e("../lib/oop"),
      i = e("../lib/lang"),
      s = e("./text_highlight_rules").TextHighlightRules,
      o = function () {
        var e = i.arrayToMap(
            (function () {
              var e = "-webkit-|-moz-|-o-|-ms-|-svg-|-pie-|-khtml-".split("|"),
                t =
                  "appearance|background-clip|background-inline-policy|background-origin|background-size|binding|border-bottom-colors|border-left-colors|border-right-colors|border-top-colors|border-end|border-end-color|border-end-style|border-end-width|border-image|border-start|border-start-color|border-start-style|border-start-width|box-align|box-direction|box-flex|box-flexgroup|box-ordinal-group|box-orient|box-pack|box-sizing|column-count|column-gap|column-width|column-rule|column-rule-width|column-rule-style|column-rule-color|float-edge|font-feature-settings|font-language-override|force-broken-image-icon|image-region|margin-end|margin-start|opacity|outline|outline-color|outline-offset|outline-radius|outline-radius-bottomleft|outline-radius-bottomright|outline-radius-topleft|outline-radius-topright|outline-style|outline-width|padding-end|padding-start|stack-sizing|tab-size|text-blink|text-decoration-color|text-decoration-line|text-decoration-style|transform|transform-origin|transition|transition-delay|transition-duration|transition-property|transition-timing-function|user-focus|user-input|user-modify|user-select|window-shadow|border-radius".split(
                    "|",
                  ),
                n =
                  "azimuth|background-attachment|background-color|background-image|background-position|background-repeat|background|border-bottom-color|border-bottom-style|border-bottom-width|border-bottom|border-collapse|border-color|border-left-color|border-left-style|border-left-width|border-left|border-right-color|border-right-style|border-right-width|border-right|border-spacing|border-style|border-top-color|border-top-style|border-top-width|border-top|border-width|border|bottom|box-shadow|box-sizing|caption-side|clear|clip|color|content|counter-increment|counter-reset|cue-after|cue-before|cue|cursor|direction|display|elevation|empty-cells|float|font-family|font-size-adjust|font-size|font-stretch|font-style|font-variant|font-weight|font|height|left|letter-spacing|line-height|list-style-image|list-style-position|list-style-type|list-style|margin-bottom|margin-left|margin-right|margin-top|marker-offset|margin|marks|max-height|max-width|min-height|min-width|opacity|orphans|outline-color|outline-style|outline-width|outline|overflow|overflow-x|overflow-y|padding-bottom|padding-left|padding-right|padding-top|padding|page-break-after|page-break-before|page-break-inside|page|pause-after|pause-before|pause|pitch-range|pitch|play-during|position|quotes|richness|right|size|speak-header|speak-numeral|speak-punctuation|speech-rate|speak|stress|table-layout|text-align|text-decoration|text-indent|text-shadow|text-transform|top|unicode-bidi|vertical-align|visibility|voice-family|volume|white-space|widows|width|word-spacing|z-index".split(
                    "|",
                  ),
                r = [];
              for (var i = 0, s = e.length; i < s; i++)
                Array.prototype.push.apply(
                  r,
                  (e[i] + t.join("|" + e[i])).split("|"),
                );
              return (
                Array.prototype.push.apply(r, t),
                Array.prototype.push.apply(r, n),
                r
              );
            })(),
          ),
          t = i.arrayToMap(
            "hsl|hsla|rgb|rgba|url|attr|counter|counters|abs|adjust_color|adjust_hue|alpha|join|blue|ceil|change_color|comparable|complement|darken|desaturate|floor|grayscale|green|hue|if|invert|join|length|lighten|lightness|mix|nth|opacify|opacity|percentage|quote|red|round|saturate|saturation|scale_color|transparentize|type_of|unit|unitless|unquote".split(
              "|",
            ),
          ),
          n = i.arrayToMap(
            "absolute|all-scroll|always|armenian|auto|baseline|below|bidi-override|block|bold|bolder|border-box|both|bottom|break-all|break-word|capitalize|center|char|circle|cjk-ideographic|col-resize|collapse|content-box|crosshair|dashed|decimal-leading-zero|decimal|default|disabled|disc|distribute-all-lines|distribute-letter|distribute-space|distribute|dotted|double|e-resize|ellipsis|fixed|georgian|groove|hand|hebrew|help|hidden|hiragana-iroha|hiragana|horizontal|ideograph-alpha|ideograph-numeric|ideograph-parenthesis|ideograph-space|inactive|inherit|inline-block|inline|inset|inside|inter-ideograph|inter-word|italic|justify|katakana-iroha|katakana|keep-all|left|lighter|line-edge|line-through|line|list-item|loose|lower-alpha|lower-greek|lower-latin|lower-roman|lowercase|lr-tb|ltr|medium|middle|move|n-resize|ne-resize|newspaper|no-drop|no-repeat|nw-resize|none|normal|not-allowed|nowrap|oblique|outset|outside|overline|pointer|progress|relative|repeat-x|repeat-y|repeat|right|ridge|row-resize|rtl|s-resize|scroll|se-resize|separate|small-caps|solid|square|static|strict|super|sw-resize|table-footer-group|table-header-group|tb-rl|text-bottom|text-top|text|thick|thin|top|transparent|underline|upper-alpha|upper-latin|upper-roman|uppercase|vertical-ideographic|vertical-text|visible|w-resize|wait|whitespace|zero".split(
              "|",
            ),
          ),
          r = i.arrayToMap(
            "aliceblue|antiquewhite|aqua|aquamarine|azure|beige|bisque|black|blanchedalmond|blue|blueviolet|brown|burlywood|cadetblue|chartreuse|chocolate|coral|cornflowerblue|cornsilk|crimson|cyan|darkblue|darkcyan|darkgoldenrod|darkgray|darkgreen|darkgrey|darkkhaki|darkmagenta|darkolivegreen|darkorange|darkorchid|darkred|darksalmon|darkseagreen|darkslateblue|darkslategray|darkslategrey|darkturquoise|darkviolet|deeppink|deepskyblue|dimgray|dimgrey|dodgerblue|firebrick|floralwhite|forestgreen|fuchsia|gainsboro|ghostwhite|gold|goldenrod|gray|green|greenyellow|grey|honeydew|hotpink|indianred|indigo|ivory|khaki|lavender|lavenderblush|lawngreen|lemonchiffon|lightblue|lightcoral|lightcyan|lightgoldenrodyellow|lightgray|lightgreen|lightgrey|lightpink|lightsalmon|lightseagreen|lightskyblue|lightslategray|lightslategrey|lightsteelblue|lightyellow|lime|limegreen|linen|magenta|maroon|mediumaquamarine|mediumblue|mediumorchid|mediumpurple|mediumseagreen|mediumslateblue|mediumspringgreen|mediumturquoise|mediumvioletred|midnightblue|mintcream|mistyrose|moccasin|navajowhite|navy|oldlace|olive|olivedrab|orange|orangered|orchid|palegoldenrod|palegreen|paleturquoise|palevioletred|papayawhip|peachpuff|peru|pink|plum|powderblue|purple|rebeccapurple|red|rosybrown|royalblue|saddlebrown|salmon|sandybrown|seagreen|seashell|sienna|silver|skyblue|slateblue|slategray|slategrey|snow|springgreen|steelblue|tan|teal|thistle|tomato|turquoise|violet|wheat|white|whitesmoke|yellow|yellowgreen".split(
              "|",
            ),
          ),
          s = i.arrayToMap(
            "@mixin|@extend|@include|@import|@media|@debug|@warn|@if|@for|@each|@while|@else|@font-face|@-webkit-keyframes|if|and|!default|module|def|end|declare".split(
              "|",
            ),
          ),
          o = i.arrayToMap(
            "a|abbr|acronym|address|applet|area|article|aside|audio|b|base|basefont|bdo|big|blockquote|body|br|button|canvas|caption|center|cite|code|col|colgroup|command|datalist|dd|del|details|dfn|dir|div|dl|dt|em|embed|fieldset|figcaption|figure|font|footer|form|frame|frameset|h1|h2|h3|h4|h5|h6|head|header|hgroup|hr|html|i|iframe|img|input|ins|keygen|kbd|label|legend|li|link|map|mark|menu|meta|meter|nav|noframes|noscript|object|ol|optgroup|option|output|p|param|pre|progress|q|rp|rt|ruby|s|samp|script|section|select|small|source|span|strike|strong|style|sub|summary|sup|table|tbody|td|textarea|tfoot|th|thead|time|title|tr|tt|u|ul|var|video|wbr|xmp".split(
              "|",
            ),
          ),
          u = "\\-?(?:(?:[0-9]+)|(?:[0-9]*\\.[0-9]+))";
        this.$rules = {
          start: [
            { token: "comment", regex: "\\/\\/.*$" },
            { token: "comment", regex: "\\/\\*", next: "comment" },
            { token: "string", regex: '["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]' },
            { token: "string", regex: '["].*\\\\$', next: "qqstring" },
            { token: "string", regex: "['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']" },
            { token: "string", regex: "['].*\\\\$", next: "qstring" },
            {
              token: "constant.numeric",
              regex:
                u + "(?:em|ex|px|cm|mm|in|pt|pc|deg|rad|grad|ms|s|hz|khz|%)",
            },
            { token: "constant.numeric", regex: "#[a-f0-9]{6}" },
            { token: "constant.numeric", regex: "#[a-f0-9]{3}" },
            { token: "constant.numeric", regex: u },
            {
              token: ["support.function", "string", "support.function"],
              regex: "(url\\()(.*)(\\))",
            },
            {
              token: function (i) {
                return e.hasOwnProperty(i.toLowerCase())
                  ? "support.type"
                  : s.hasOwnProperty(i)
                    ? "keyword"
                    : n.hasOwnProperty(i)
                      ? "constant.language"
                      : t.hasOwnProperty(i)
                        ? "support.function"
                        : r.hasOwnProperty(i.toLowerCase())
                          ? "support.constant.color"
                          : o.hasOwnProperty(i.toLowerCase())
                            ? "variable.language"
                            : "text";
              },
              regex: "\\-?[@a-z_][@a-z0-9_\\-]*",
            },
            { token: "variable", regex: "[a-z_\\-$][a-z0-9_\\-$]*\\b" },
            { token: "variable.language", regex: "#[a-z0-9-_]+" },
            { token: "variable.language", regex: "\\.[a-z0-9-_]+" },
            { token: "variable.language", regex: ":[a-z0-9-_]+" },
            { token: "constant", regex: "[a-z0-9-_]+" },
            {
              token: "keyword.operator",
              regex: "<|>|<=|>=|==|!=|-|%|#|\\+|\\$|\\+|\\*",
            },
            { token: "paren.lparen", regex: "[[({]" },
            { token: "paren.rparen", regex: "[\\])}]" },
            { token: "text", regex: "\\s+" },
            { caseInsensitive: !0 },
          ],
          comment: [
            { token: "comment", regex: "\\*\\/", next: "start" },
            { defaultToken: "comment" },
          ],
          qqstring: [
            {
              token: "string",
              regex: '(?:(?:\\\\.)|(?:[^"\\\\]))*?"',
              next: "start",
            },
            { token: "string", regex: ".+" },
          ],
          qstring: [
            {
              token: "string",
              regex: "(?:(?:\\\\.)|(?:[^'\\\\]))*?'",
              next: "start",
            },
            { token: "string", regex: ".+" },
          ],
        };
      };
    r.inherits(o, s), (t.ScssHighlightRules = o);
  },
),
  define(
    "ace/mode/matching_brace_outdent",
    ["require", "exports", "module", "ace/range"],
    function (e, t, n) {
      "use strict";
      var r = e("../range").Range,
        i = function () {};
      (function () {
        (this.checkOutdent = function (e, t) {
          return /^\s+$/.test(e) ? /^\s*\}/.test(t) : !1;
        }),
          (this.autoOutdent = function (e, t) {
            var n = e.getLine(t),
              i = n.match(/^(\s*\})/);
            if (!i) return 0;
            var s = i[1].length,
              o = e.findMatchingBracket({ row: t, column: s });
            if (!o || o.row == t) return 0;
            var u = this.$getIndent(e.getLine(o.row));
            e.replace(new r(t, 0, t, s - 1), u);
          }),
          (this.$getIndent = function (e) {
            return e.match(/^\s*/)[0];
          });
      }).call(i.prototype),
        (t.MatchingBraceOutdent = i);
    },
  ),
  define(
    "ace/mode/behaviour/css",
    [
      "require",
      "exports",
      "module",
      "ace/lib/oop",
      "ace/mode/behaviour",
      "ace/mode/behaviour/cstyle",
      "ace/token_iterator",
    ],
    function (e, t, n) {
      "use strict";
      var r = e("../../lib/oop"),
        i = e("../behaviour").Behaviour,
        s = e("./cstyle").CstyleBehaviour,
        o = e("../../token_iterator").TokenIterator,
        u = function () {
          this.inherit(s),
            this.add("colon", "insertion", function (e, t, n, r, i) {
              if (i === ":" && n.selection.isEmpty()) {
                var s = n.getCursorPosition(),
                  u = new o(r, s.row, s.column),
                  a = u.getCurrentToken();
                a && a.value.match(/\s+/) && (a = u.stepBackward());
                if (a && a.type === "support.type") {
                  var f = r.doc.getLine(s.row),
                    l = f.substring(s.column, s.column + 1);
                  if (l === ":") return { text: "", selection: [1, 1] };
                  if (/^(\s+[^;]|\s*$)/.test(f.substring(s.column)))
                    return { text: ":;", selection: [1, 1] };
                }
              }
            }),
            this.add("colon", "deletion", function (e, t, n, r, i) {
              var s = r.doc.getTextRange(i);
              if (!i.isMultiLine() && s === ":") {
                var u = n.getCursorPosition(),
                  a = new o(r, u.row, u.column),
                  f = a.getCurrentToken();
                f && f.value.match(/\s+/) && (f = a.stepBackward());
                if (f && f.type === "support.type") {
                  var l = r.doc.getLine(i.start.row),
                    c = l.substring(i.end.column, i.end.column + 1);
                  if (c === ";") return i.end.column++, i;
                }
              }
            }),
            this.add("semicolon", "insertion", function (e, t, n, r, i) {
              if (i === ";" && n.selection.isEmpty()) {
                var s = n.getCursorPosition(),
                  o = r.doc.getLine(s.row),
                  u = o.substring(s.column, s.column + 1);
                if (u === ";") return { text: "", selection: [1, 1] };
              }
            }),
            this.add("!important", "insertion", function (e, t, n, r, i) {
              if (i === "!" && n.selection.isEmpty()) {
                var s = n.getCursorPosition(),
                  o = r.doc.getLine(s.row);
                if (/^\s*(;|}|$)/.test(o.substring(s.column)))
                  return { text: "!important", selection: [10, 10] };
              }
            });
        };
      r.inherits(u, s), (t.CssBehaviour = u);
    },
  ),
  define(
    "ace/mode/folding/cstyle",
    [
      "require",
      "exports",
      "module",
      "ace/lib/oop",
      "ace/range",
      "ace/mode/folding/fold_mode",
    ],
    function (e, t, n) {
      "use strict";
      var r = e("../../lib/oop"),
        i = e("../../range").Range,
        s = e("./fold_mode").FoldMode,
        o = (t.FoldMode = function (e) {
          e &&
            ((this.foldingStartMarker = new RegExp(
              this.foldingStartMarker.source.replace(
                /\|[^|]*?$/,
                "|" + e.start,
              ),
            )),
            (this.foldingStopMarker = new RegExp(
              this.foldingStopMarker.source.replace(/\|[^|]*?$/, "|" + e.end),
            )));
        });
      r.inherits(o, s),
        function () {
          (this.foldingStartMarker = /([\{\[\(])[^\}\]\)]*$|^\s*(\/\*)/),
            (this.foldingStopMarker = /^[^\[\{\(]*([\}\]\)])|^[\s\*]*(\*\/)/),
            (this.singleLineBlockCommentRe = /^\s*(\/\*).*\*\/\s*$/),
            (this.tripleStarBlockCommentRe = /^\s*(\/\*\*\*).*\*\/\s*$/),
            (this.startRegionRe = /^\s*(\/\*|\/\/)#?region\b/),
            (this._getFoldWidgetBase = this.getFoldWidget),
            (this.getFoldWidget = function (e, t, n) {
              var r = e.getLine(n);
              if (
                this.singleLineBlockCommentRe.test(r) &&
                !this.startRegionRe.test(r) &&
                !this.tripleStarBlockCommentRe.test(r)
              )
                return "";
              var i = this._getFoldWidgetBase(e, t, n);
              return !i && this.startRegionRe.test(r) ? "start" : i;
            }),
            (this.getFoldWidgetRange = function (e, t, n, r) {
              var i = e.getLine(n);
              if (this.startRegionRe.test(i))
                return this.getCommentRegionBlock(e, i, n);
              var s = i.match(this.foldingStartMarker);
              if (s) {
                var o = s.index;
                if (s[1]) return this.openingBracketBlock(e, s[1], n, o);
                var u = e.getCommentFoldRange(n, o + s[0].length, 1);
                return (
                  u &&
                    !u.isMultiLine() &&
                    (r
                      ? (u = this.getSectionRange(e, n))
                      : t != "all" && (u = null)),
                  u
                );
              }
              if (t === "markbegin") return;
              var s = i.match(this.foldingStopMarker);
              if (s) {
                var o = s.index + s[0].length;
                return s[1]
                  ? this.closingBracketBlock(e, s[1], n, o)
                  : e.getCommentFoldRange(n, o, -1);
              }
            }),
            (this.getSectionRange = function (e, t) {
              var n = e.getLine(t),
                r = n.search(/\S/),
                s = t,
                o = n.length;
              t += 1;
              var u = t,
                a = e.getLength();
              while (++t < a) {
                n = e.getLine(t);
                var f = n.search(/\S/);
                if (f === -1) continue;
                if (r > f) break;
                var l = this.getFoldWidgetRange(e, "all", t);
                if (l) {
                  if (l.start.row <= s) break;
                  if (l.isMultiLine()) t = l.end.row;
                  else if (r == f) break;
                }
                u = t;
              }
              return new i(s, o, u, e.getLine(u).length);
            }),
            (this.getCommentRegionBlock = function (e, t, n) {
              var r = t.search(/\s*$/),
                s = e.getLength(),
                o = n,
                u = /^\s*(?:\/\*|\/\/|--)#?(end)?region\b/,
                a = 1;
              while (++n < s) {
                t = e.getLine(n);
                var f = u.exec(t);
                if (!f) continue;
                f[1] ? a-- : a++;
                if (!a) break;
              }
              var l = n;
              if (l > o) return new i(o, r, l, t.length);
            });
        }.call(o.prototype);
    },
  ),
  define(
    "ace/mode/scss",
    [
      "require",
      "exports",
      "module",
      "ace/lib/oop",
      "ace/mode/text",
      "ace/mode/scss_highlight_rules",
      "ace/mode/matching_brace_outdent",
      "ace/mode/behaviour/css",
      "ace/mode/folding/cstyle",
    ],
    function (e, t, n) {
      "use strict";
      var r = e("../lib/oop"),
        i = e("./text").Mode,
        s = e("./scss_highlight_rules").ScssHighlightRules,
        o = e("./matching_brace_outdent").MatchingBraceOutdent,
        u = e("./behaviour/css").CssBehaviour,
        a = e("./folding/cstyle").FoldMode,
        f = function () {
          (this.HighlightRules = s),
            (this.$outdent = new o()),
            (this.$behaviour = new u()),
            (this.foldingRules = new a());
        };
      r.inherits(f, i),
        function () {
          (this.lineCommentStart = "//"),
            (this.blockComment = { start: "/*", end: "*/" }),
            (this.getNextLineIndent = function (e, t, n) {
              var r = this.$getIndent(t),
                i = this.getTokenizer().getLineTokens(t, e).tokens;
              if (i.length && i[i.length - 1].type == "comment") return r;
              var s = t.match(/^.*\{\s*$/);
              return s && (r += n), r;
            }),
            (this.checkOutdent = function (e, t, n) {
              return this.$outdent.checkOutdent(t, n);
            }),
            (this.autoOutdent = function (e, t, n) {
              this.$outdent.autoOutdent(t, n);
            }),
            (this.$id = "ace/mode/scss");
        }.call(f.prototype),
        (t.Mode = f);
    },
  );
(function () {
  window.require(["ace/mode/scss"], function (m) {
    if (typeof module == "object" && typeof exports == "object" && module) {
      module.exports = m;
    }
  });
})();
