// IOnNewBookArrivedListener.aidl
package carousel.em.com.ipctest;

// Declare any non-default types here with import statements
import carousel.em.com.ipctest.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void onNewBookArrived(in Book newBook);
}
