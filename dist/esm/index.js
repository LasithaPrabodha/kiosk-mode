import { registerPlugin } from '@capacitor/core';
const KioskMode = registerPlugin('KioskMode', {
    web: () => import('./web').then(m => new m.KioskModeWeb()),
});
export * from './definitions';
export { KioskMode };
//# sourceMappingURL=index.js.map