// IsecurityCenter.aidl
package carousel.em.com.ipctest;

// Declare any non-default types here with import statements

interface IsecurityCenter {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     String encrypt(String content);
     String decrypt(String password);
}
