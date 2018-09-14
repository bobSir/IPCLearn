// IBookManager.aidl
package carousel.em.com.ipctest;

// Declare any non-default types here with import statements
import carousel.em.com.ipctest.Book;
import carousel.em.com.ipctest.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
}
