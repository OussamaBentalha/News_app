package com.esgi.android.news.metier.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.esgi.android.news.metier.listener.AutoRefreshTask;
import com.esgi.android.news.metier.model.IRefreshable;

/**
 * Created by Sam on 22/06/16.
 */
public class Refresher implements IRefreshable {

    /**
     * La tâche utilisée pour le rafraichissement.
     */
    public AsyncTask<?, ?, ?> refreshTask;

    /**
     * La tâche utilisée pour le rafraichissement automatique.
     */
    public AutoRefreshTask ra;

    /**
     * Si l'activité est sensée se rafraichir toute seule toutes les x secondes, ou si un rafraichissement uniquement manuel est autorisé.
     */
    public Boolean backgroundRefresh = true;

    /**
     *
     */
    public IRefreshable owner;

    public Refresher(IRefreshable owner) {
        this.owner = owner;
    }

    /**
     *
     * @return l'interval de rafraichissement, en ms.
     */
    public int getRefreshInterval() {
        return 5000;
    }

    /**
     * Arrête le rafraichissement périodique (si nécessaire) et arrête la tâche qui rafraichit.
     */
    public void stopRefresh() {
        if (backgroundRefresh) {
            if (ra != null) {
                ra.interrupt();
            }
        }
        cancelRefresh();
    }

    /**
     * Comment le rafraichissement périodique (si nécessaire, avec {@link #backgroundRefresh}) ou lance un rafrachissement.
     */
    public void startRefresh() {
        if (backgroundRefresh) {
            if (ra == null || ra.getState() == Thread.State.TERMINATED) {
                int ref = getRefreshInterval();
                ra = new AutoRefreshTask(this, ref);
            }
            try {
                ra.start();
            } catch (IllegalThreadStateException e) {
                Log.e(getClass().getSimpleName(), "Refresh thread already started ! thread state is " + ra.getState(), e);
                forceRefresh();
            }
        } else {
            this.refresh();
        }
    }

    /**
     * Rafraichissement manuel.
     */
    public void forceRefresh() {
        if (backgroundRefresh) {
            synchronized (ra) {
                ra.notify();
            }
        } else {
            this.refresh();
        }
    }

    /*
    * Stoppe la tâche de rafraichissement si elle existe. (non-Javadoc)
    *
     * @see fr.android.mobineo.activity.IActivityRefreshable#cancelRefresh()
    */
    @Override
    public void cancelRefresh() {
        if (refreshTask != null) {
            refreshTask.cancel(true);
        }
    }

    @Override
    public void refresh() {
        owner.refresh();
    }

}
