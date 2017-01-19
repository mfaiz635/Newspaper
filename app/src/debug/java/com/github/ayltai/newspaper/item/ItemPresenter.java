package com.github.ayltai.newspaper.item;

import android.support.annotation.NonNull;

import com.github.ayltai.newspaper.Constants;

import io.realm.Realm;

public class ItemPresenter extends BaseItemPresenter {
    public ItemPresenter(@NonNull final Realm realm) {
        super(realm);
    }

    @Override
    protected void attachClicks() {
        if (this.getView().clicks() != null) this.subscriptions.add(this.getView().clicks().subscribe(dummy -> {
            if (this.parentKey != null) {
                this.getView().showItem(this.parentKey, this.item);
            }
        }, error -> this.log().e(this.getClass().getSimpleName(), error.getMessage(), error)));
    }

    @Override
    protected void attachBookmarks() {
        if (this.getView().bookmarks() != null) this.subscriptions.add(this.getView().bookmarks().subscribe(bookmark -> this.getFeedManager().getFeed(Constants.SOURCE_BOOKMARK).subscribe(feed -> {
            this.updateFeed(feed, bookmark);
        }, error -> this.log().e(this.getClass().getSimpleName(), error.getMessage(), error)), error -> this.log().e(this.getClass().getSimpleName(), error.getMessage(), error)));
    }

    @Override
    protected void attachShares() {
        if (this.getView().shares() != null) this.subscriptions.add(this.getView().shares().subscribe(dummy -> {
            if (this.item != null && this.item.getLink() != null) {
                this.getView().share(this.item.getLink());
            }
        }, error -> this.log().e(this.getClass().getSimpleName(), error.getMessage(), error)));
    }
}