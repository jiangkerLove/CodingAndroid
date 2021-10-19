package jfp.study.ipc.pool;

interface AidlPool{
    IBinder getBinder(String id);
}