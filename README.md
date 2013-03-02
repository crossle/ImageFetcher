ImageFetcher Library
=========================

This folder contains the main library which should be linked against as an
Android library project in your application, it's backport iosched and add load image from local.
It's used LruCache, Asynchronous load remote and local image.

Useage
-------
 * Init ImageFetcher

```java
ImageFetcher mImageFetcher = UIUtils.getImageFetcher(getActivity());
```
 * Load image include loadImage and load ThumnailImage

```java
mImageFetcher.loadThumbnailImage(speakerImageUrl, speakerImageView, R.drawable.person_image_empty);
```
 * Flush close cache

```java
@Override
public void onPause() {
    super.onPause();
    mImageFetcher.flushCache();
}

@Override
public void onDestroy() {
        super.onDestroy();
    mImageFetcher.closeCache();
}
```
 * Options, you can load image when listview SCROLL_STATE_IDLE

```java
 @Override
public void onScrollStateChanged(AbsListView listView, int scrollState) {
    // Pause disk cache access to ensure smoother scrolling
    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING ||
            scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
        mImageFetcher.setPauseWork(true);
    } else {
        mImageFetcher.setPauseWork(false);
    }
}
```
License
=======

    Copyright 2012 Crossle Song

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

