import { WebPlugin } from '@capacitor/core';
import type { KioskModePlugin } from './definitions';
export declare class KioskModeWeb extends WebPlugin implements KioskModePlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
