
public abstract class UndoableCommand implements Command {
	@Override
	public abstract void execute();

	public abstract void undo();
}
