'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const KioskMode = core.registerPlugin('KioskMode', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.KioskModeWeb()),
});

class KioskModeWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    KioskModeWeb: KioskModeWeb
});

exports.KioskMode = KioskMode;
//# sourceMappingURL=plugin.cjs.js.map
