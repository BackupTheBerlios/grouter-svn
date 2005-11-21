package codesnipps.designpatterns.command;

/**
 * Command could be an interface or abstract class.
 * @author Georges
 *
 */
abstract class Command
{
    protected Message message;
    abstract void execute();
}
