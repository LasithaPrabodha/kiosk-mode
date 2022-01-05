import { WebPlugin } from '@capacitor/core';
import type { KioskModePlugin } from './definitions';
export declare class KioskModeWeb extends WebPlugin implements KioskModePlugin {
    enable(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    disable(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
