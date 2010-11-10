package frame;

public interface Engine<P, O> {
    public O resolve(P target);
}
