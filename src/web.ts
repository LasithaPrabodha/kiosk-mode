import { WebPlugin } from '@capacitor/core';

import type { KioskModePlugin } from './definitions';

export class KioskModeWeb extends WebPlugin implements KioskModePlugin {
  async enable(options: { value: string }): Promise<{ value: string }> {
    return options;
  }
  async disable(options: { value: string }): Promise<{ value: string }> {
    return options;
  }
}
