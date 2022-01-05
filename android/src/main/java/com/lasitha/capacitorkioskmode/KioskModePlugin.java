package com.lasitha.capacitorkioskmode;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "KioskMode")
public class KioskModePlugin extends Plugin {

    private KioskMode implementation = new KioskMode();


    @PluginMethod
    public void enable(PluginCall call) {

        Boolean isAdmin = implementation.isAdmin();

        implementation.setKioskPolicies(true, isAdmin);

        JSObject ret = new JSObject();
        ret.put("enabled", true);
        call.resolve(ret);
    }

    @PluginMethod
    public void disable(PluginCall call) {

        Boolean isAdmin = implementation.isAdmin();
        implementation.setKioskPolicies(false, isAdmin);

        JSObject ret = new JSObject();
        ret.put("enabled", false);
        call.resolve(ret);

    }

}
