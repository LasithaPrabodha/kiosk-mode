export interface KioskModePlugin {
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
