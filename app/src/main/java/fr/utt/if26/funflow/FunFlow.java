package fr.utt.if26.funflow;

import android.app.Application;
import android.content.Context;

import fr.utt.if26.funflow.databaseAccessHub.DBController;

public class FunFlow extends Application {
    private static FunFlow FunFlow;
    private static DBController controller;

    public static FunFlow getApplication() {
        return FunFlow;
    }

    public static DBController getController() { return controller; }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static void setController(DBController ctrl)
    {
        controller = ctrl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FunFlow = this;
    }
}
