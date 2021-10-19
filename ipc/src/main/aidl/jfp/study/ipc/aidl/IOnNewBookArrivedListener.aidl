package jfp.study.ipc.aidl;

import jfp.study.ipc.aidl.Book;

interface IOnNewBookArrivedListener{
    void onNewBookArrived(in Book newBook);
}