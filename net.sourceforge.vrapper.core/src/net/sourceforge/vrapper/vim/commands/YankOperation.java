package net.sourceforge.vrapper.vim.commands;

import net.sourceforge.vrapper.utils.ContentType;
import net.sourceforge.vrapper.utils.TextRange;
import net.sourceforge.vrapper.vim.EditorAdaptor;
import net.sourceforge.vrapper.vim.Options;
import net.sourceforge.vrapper.vim.register.RegisterContent;
import net.sourceforge.vrapper.vim.register.StringRegisterContent;

public class YankOperation extends SimpleTextOperation {

    public static final YankOperation INSTANCE = new YankOperation();

    private YankOperation() { /* NOP */ }

    @Override
    public void execute(EditorAdaptor editorAdaptor, TextRange region, ContentType contentType) {
        doIt(editorAdaptor, region, contentType);
    }

    public TextOperation repetition() {
        return null;
    }

    public static void doIt(EditorAdaptor editorAdaptor, TextRange range, ContentType contentType) {
        String text = editorAdaptor.getModelContent().getText(range.getLeftBound().getModelOffset(), range.getModelLength());
        RegisterContent content = new StringRegisterContent(contentType, text);
        editorAdaptor.getRegisterManager().getActiveRegister().setContent(content);
        if (editorAdaptor.getConfiguration().get(Options.MOVE_ON_YANK).booleanValue())
            editorAdaptor.getCursorService().setPosition(range.getLeftBound(), true);
    }

}
