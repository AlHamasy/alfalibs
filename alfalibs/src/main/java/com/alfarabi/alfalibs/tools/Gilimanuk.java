package com.alfarabi.alfalibs.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import org.fingerlinks.mobile.android.navigator.Navigator;
import org.fingerlinks.mobile.android.navigator.builder.Builders.Any.F;
import org.fingerlinks.mobile.android.navigator.builder.Builders.Any.G;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Gilimanuk {
    public static final String TAG = Gilimanuk.class.getName();

    static int animIn = 0 ;
    static int animOut = 0 ;
    static int popIn = 0 ;
    static int popOut = 0 ;
    static boolean force = false ;

    public Gilimanuk() {

    }

    public static Gilimanuk withAnim(int animIn, int animOut){
        Gilimanuk.animIn = animIn ;
        Gilimanuk.animOut = animOut ;
        return new Gilimanuk() ;
    }

    public static Gilimanuk withAnim(int animIn, int animOut, int popIn, int popOut){
        Gilimanuk.animIn = animIn ;
        Gilimanuk.animOut = animOut ;
        Gilimanuk.popIn = popIn ;
        Gilimanuk.popOut = popOut ;
        return new Gilimanuk() ;
    }
    public static Gilimanuk withAnim(int... anim){
        Gilimanuk.animIn = anim[0] ;
        Gilimanuk.animOut = anim[1] ;
        Gilimanuk.popIn = anim[2] ;
        Gilimanuk.popOut = anim[3] ;
        return new Gilimanuk() ;
    }

    public static Gilimanuk force(){
        Gilimanuk.force = true ;
        return new Gilimanuk();
    }




    public static void gotoAnotherApps(Context context, @NonNull String packageName) {
        Observable.fromCallable(() -> {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                } catch (Exception anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
            return true ;
        }).subscribeOn(Schedulers.newThread()).subscribe();

    }

    public static void gotoMarketApps(Context context, @NonNull String packageName) {
        Observable.fromCallable(() -> {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (Exception anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
            return true ;
        }).subscribeOn(Schedulers.newThread()).subscribe();


    }

    public Disposable goFirst(FragmentActivity activity, String tag, int... id){
        return goFirst(activity, tag, id[0]);
    }
    public static Disposable goFirst(FragmentActivity activity, String tag, int id) {
        return Observable.fromCallable(() -> {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(tag, id)).tag(tag);
                F.addToBackStack();
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                F.replace().commit();
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                force = false ;
                return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
            }
        }).subscribe();
    }

    public Disposable goFirst(FragmentActivity activity, String tag, Bundle bundle, int... id){
        return goFirst(activity, tag, bundle, id[0]);
    }
    public static Disposable goFirst(FragmentActivity activity, String tag, Bundle bundle, int id) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(tag, bundle, id)).tag(tag);
                F.addToBackStack();
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                F.replace().commit();
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                force = false ;
                return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
            }
        }).subscribe();
    }

    public Disposable goTo(FragmentActivity activity, String tag, int id, boolean... backStack) {
        return goTo(activity, tag, id, backStack[0]);
    }
    public static Disposable goTo(FragmentActivity activity, String tag, int id, boolean backStack) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(tag, id)).tag(tag);
                if(backStack) {
                    F.addToBackStack();
                }
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                F.add().commit();
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                force = false ;
                return activity.getSupportFragmentManager().findFragmentByTag(tag);
            }
        }).subscribe();
    }

    public Disposable goTo(FragmentActivity activity, Fragment fragment, String tag, int id, boolean... backStack) {
        return goTo(activity, fragment, tag, id, backStack[0]);
    }
    public static Disposable goTo(FragmentActivity activity, Fragment fragment, String tag, int id, boolean backStack) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(fragment, id)).tag(tag);
                if(backStack) {
                    F.addToBackStack();
                }
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                F.add().commit();
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                force = false ;
                return activity.getSupportFragmentManager().findFragmentByTag(tag);
            }
        }).subscribe();


    }

    public static Disposable goTo(FragmentActivity activity, String tag, Bundle bundle, int id, boolean... backStack) {
        return goTo(activity, tag, bundle, id, backStack[0]);
    }
    public static Disposable goTo(FragmentActivity activity, String tag, Bundle bundle, int id, boolean backStack) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(tag, bundle, id)).tag(tag);
                if(backStack) {
                    F.addToBackStack();
                }
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                F.add().commit();
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                force = false ;
                return activity.getSupportFragmentManager().findFragmentByTag(tag);
            }
        }).subscribe();
    }

    public Disposable goTo(FragmentActivity activity, String tag, int id, boolean backStack, boolean... add) {
        return goTo(activity, tag, id, backStack, add[0]);
    }
    public static Disposable goTo(FragmentActivity activity, String tag, int id, boolean backStack, boolean add) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(tag, id)).tag(tag);
                if(backStack) {
                    F.addToBackStack();
                }
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                if(add){
                    F.add().commit();
                }else{
                    F.replace().commit();
                }
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                return activity.getSupportFragmentManager().findFragmentByTag(tag);
            }
        }).subscribe();
    }

    public Disposable goTo(FragmentActivity activity, Fragment fragment, String tag, int id, boolean backStack, boolean... add) {
        return goTo(activity, fragment, tag, id, backStack, add[0]);
    }
    public static Disposable goTo(FragmentActivity activity, Fragment fragment, String tag, int id, boolean backStack, boolean add) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(fragment, id)).tag(tag);
                if(backStack) {
                    F.addToBackStack();
                }
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                if(add){
                    F.add().commit();
                }else{
                    F.replace().commit();
                }
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                return activity.getSupportFragmentManager().findFragmentByTag(tag);
            }
        }).subscribe();
    }

    public Disposable goTo(FragmentActivity activity, String tag, Bundle bundle, int id, boolean backStack, boolean... add) {
        return goTo(activity, tag, bundle, id, backStack, add[0]);
    }
    public static Disposable goTo(FragmentActivity activity, String tag, Bundle bundle, int id, boolean backStack, boolean add) {

        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            if(currentIs(activity, tag) && !force) {
                return currentFragment(activity);
            } else {
                WLog.i(TAG, "GO TO = " + tag);
                G G = Navigator.with(activity).build();
                F F = (F)((F)G.goTo(tag, bundle, id)).tag(tag);
                if(backStack) {
                    F.addToBackStack();
                }
                if(animIn!=0 && animOut!=0 && popIn!=0 && popOut!=0){
                    F.animation(animIn, animOut, popIn, popOut);
                }else if(animIn!=0 && animOut!=0){
                    F.animation(animIn, animOut);
                }
                if(add){
                    F.add().commit();
                }else{
                    F.replace().commit();
                }
                Gilimanuk.animIn = 0 ;
                Gilimanuk.animOut = 0 ;
                Gilimanuk.popIn = 0 ;
                Gilimanuk.popOut = 0 ;
                return activity.getSupportFragmentManager().findFragmentByTag(tag);
            }
        }).subscribe();
    }

    public static Disposable backstack(FragmentActivity activity) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            boolean canGoBack = Navigator.with(activity).utils().canGoBack(activity.getSupportFragmentManager());
            if(canGoBack) {
                Navigator.with(activity).utils().goToPreviousBackStack();
            }

            return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
        }).subscribe();
    }

    public static Disposable backstack(FragmentActivity activity, String tag, int id) {
        return Observable.fromCallable(() -> {
            activity.getSupportFragmentManager().beginTransaction().setTransition(4096);
            boolean canGoBack = Navigator.with(activity).utils().canGoBackToSpecificPoint(tag, id, activity.getSupportFragmentManager());
            if(canGoBack) {
                Navigator.with(activity).utils().goBackToSpecificPoint(tag);
            }

            return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
        }).subscribe();

    }

    public static boolean canGoBack(FragmentActivity activity) {
        try {
            boolean e = Navigator.with(activity).utils().canGoBack(activity.getSupportFragmentManager());
            return e;
        } catch (Exception var2) {
            return false;
        }
    }

    public static Disposable currentFragment(FragmentActivity activity) {
        return Observable.fromCallable(() -> {
            return actualFragmentTag(activity) == null?null:activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
        }).subscribe();

    }

    public static boolean currentIs(FragmentActivity a, Class c) {
        WLog.i(TAG, "TAG OF CURRENT FRAGMENT IS = " + actualFragmentTag(a));

        try {
            if(c.getName().equalsIgnoreCase(actualFragmentTag(a))) {
                return true;
            }
        } catch (Exception var3) {
            ;
        }

        return false;
    }

    public static boolean currentIs(FragmentActivity a, String name) {
        WLog.i(TAG, "TAG OF CURRENT FRAGMENT IS = " + actualFragmentTag(a));

        try {
            if(name.equalsIgnoreCase(actualFragmentTag(a))) {
                return true;
            }
        } catch (Exception var3) {
            ;
        }

        return false;
    }

    public static <T extends Fragment> T currentFragment(FragmentActivity activity, Class<T> cazz) {
        return (T) activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
    }

    public static <T extends Fragment> T getFragment(FragmentActivity activity, String tag) {
        return (T) activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static String actualFragmentTag(FragmentActivity activity) {
        String tag = Navigator.with(activity).utils().getActualTag();
        return tag;
    }

    public static void restart(@NonNull Context context, @NonNull Class<? extends Activity> nextActivityClass) {
        restart(context, new Intent(context, nextActivityClass));
    }

    public static void restart(@NonNull Context context, @NonNull Intent nextActivity) {
        nextActivity.addFlags(268435456);
        context.startActivity(nextActivity);
        if(context instanceof Activity) {
            ((Activity)context).finish();
        }

        Runtime.getRuntime().exit(0);
    }

    public static Intent startActivity(Context from, Class to) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        ((Activity)from).finish();
        return intent;
    }

    public static Intent startActivity(Context from, Class to, boolean finish) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        if(finish) {
            ((Activity)from).finish();
        }

        return intent;
    }

    public static Intent startActivityForResult(Context from, Class to, int requestCode) {
        Intent intent = new Intent(from, to);
        ((Activity)from).startActivityForResult(intent, requestCode);
        ((Activity)from).finish();
        return intent;
    }
}
