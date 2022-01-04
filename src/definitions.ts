export interface KioskModePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
