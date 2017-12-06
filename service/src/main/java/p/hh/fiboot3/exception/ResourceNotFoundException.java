package p.hh.fiboot3.exception;


public class ResourceNotFoundException extends Exception {
    private String resource;
    private String resourceName;

    public ResourceNotFoundException(String resource, String resourceName) {
        super(resource + " [" + resourceName + "] not found.");
    }
}
