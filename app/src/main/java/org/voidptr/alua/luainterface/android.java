package org.voidptr.alua.luainterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.voidptr.alua.MainActivity;

public class android extends TwoArgFunction {
    private static Activity activity;

    public android(Activity activity){
        android.activity = activity;
    }

    public static Activity getActivity(){
        return android.activity;
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("dialog", new dialog());
        library.set("intent", new intent());
        env.set("android", library);
        return library;
    }

    static class dialog extends TwoArgFunction{
        @Override
        public LuaValue call(LuaValue title, LuaValue message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(android.getActivity());
            builder.setMessage(message.tojstring()).setTitle(title.tojstring());
            AlertDialog dialog = builder.create();
            dialog.show();
            return null;
        }
    }

    static class intent extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue name) {
            Class<?> c = null;
            try{
                c = Class.forName(name.tojstring());
                Intent i = new Intent(android.getActivity().getApplicationContext(), c);
                android.getActivity().startActivity(i);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
