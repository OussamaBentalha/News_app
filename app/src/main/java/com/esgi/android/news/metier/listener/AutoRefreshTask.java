package com.esgi.android.news.metier.listener;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.esgi.android.news.metier.model.IRefreshable;
import com.esgi.android.news.metier.utils.Refresher;

import java.lang.ref.WeakReference;

/**
 * Created by Sam on 22/06/16.
 */
public class AutoRefreshTask extends Thread implements Runnable {

    private int timeRefresh;
    private final static int REFRESH = 1;
    private final static int CANCEL_REFRESH = 2;

    private final IRefreshable refreshable;
    private RefreshHandler handler;

    public AutoRefreshTask(IRefreshable activity, int refreshPeriod) {
        super("Refresh-" + activity.getClass().getSimpleName());
        this.refreshable = activity;
        this.timeRefresh = refreshPeriod;
        Log.i(getClass().getSimpleName(), "Temps de rafraichissement : " + timeRefresh);

        handler = new RefreshHandler(activity);
    }

    @Override
    public void run() {

        String nameActivity = refreshable.getClass().getSimpleName();
        if (refreshable instanceof Refresher) {
            Refresher r = (Refresher) refreshable;
            nameActivity = r.owner.getClass().getSimpleName();
        }
        Log.i(getClass().getSimpleName(), "Lancement thread de rafraichissement pour activity " + nameActivity + ", id = " + this.getId());

        while (!isInterrupted()) {

            long time = System.currentTimeMillis();
            handler.sendEmptyMessage(REFRESH);

            try {
                synchronized (this) {

                    if (timeRefresh == 0)
                        wait();
                    else
                        wait(timeRefresh);
                }
            } catch (InterruptedException e) {
                Log.e(getClass().getSimpleName(), "Fin refresh " + nameActivity + ", id = " + Thread.currentThread().getId());
                Thread.currentThread().interrupt(); // Activation du flag d'interruption
            }

            Log.i(getClass().getSimpleName(), "Boucle de " + (System.currentTimeMillis() - time) + " ms, id = " + this.getId());
        }
        Log.i(getClass().getSimpleName(), "Fin refresh " + nameActivity + ", id = " + Thread.currentThread().getId());
    }

    private static class RefreshHandler extends Handler {
        private final WeakReference<IRefreshable> mRefreshable;

        public RefreshHandler(IRefreshable activity) {
            mRefreshable = new WeakReference<IRefreshable>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            IRefreshable activity = mRefreshable.get();
            if (activity != null) {
                String name = activity.getClass().getSimpleName();
                if (activity instanceof Refresher) {
                    Refresher r = (Refresher) activity;
                    name = r.owner.getClass().getSimpleName();
                }
                switch (msg.what) {

                    case REFRESH:

                                                                              /* Refresh UI */
                        activity.refresh();
                        Log.i(name, "Liste rafraichie.");

                        break;
                    case CANCEL_REFRESH:

                                                                              /* Refresh UI */
                        activity.cancelRefresh();
                        Log.i(name, "Annulation rafraichissement");

                        break;
                }
            }
        }
    }

    @Override
    public void interrupt() {
        if (!isInterrupted()) {
            super.interrupt();
            if (refreshable instanceof Refresher) {
                Refresher r = (Refresher) refreshable;
                String nameActivity = r.owner.getClass().getSimpleName();
                Log.i(getClass().getSimpleName(), "Arrêt du thread de rafraichissement pour activite " + nameActivity + ", id = " + this.getId());
            } else
                Log.i(getClass().getSimpleName(), "Arrêt du thread de rafraichissement, id = " + this.getId());
        }
    }

}
