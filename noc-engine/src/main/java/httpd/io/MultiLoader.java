package httpd.io;

public class MultiLoader implements Loader {
    final protected Loader[] loaders;

    public MultiLoader(Loader... loaders) {
        this.loaders = loaders;
    }

    @Override
    public Source findSource(String name) {
        Source source = loaders[0].findSource(name);
        if (source != null) {
            return source;
        } else {
            for (int i = 1; i < loaders.length; i++) {
                source = loaders[i].findSource(name);
                if (source != null) {
                    return source;
                }
            }
        }
        return null;
    }
}
