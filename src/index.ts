import { registerPlugin } from '@capacitor/core';

import type { KioskModePlugin } from './definitions';

const KioskMode = registerPlugin<KioskModePlugin>('KioskMode', {
  web: () => import('./web').then(m => new m.KioskModeWeb()),
});

export * from './definitions';
export { KioskMode };
