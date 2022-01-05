var capacitorKioskMode = (function (exports, core) {
    'use strict';

    const KioskMode = core.registerPlugin('KioskMode', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.KioskModeWeb()),
    });

    class KioskModeWeb extends core.WebPlugin {
        async enable(options) {
            return options;
        }
        async disable(options) {
            return options;
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        KioskModeWeb: KioskModeWeb
    });

    exports.KioskMode = KioskMode;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
