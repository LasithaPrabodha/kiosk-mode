import { WebPlugin } from '@capacitor/core';

import type { KioskModePlugin } from './definitions';

export class KioskModeWeb extends WebPlugin implements KioskModePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
