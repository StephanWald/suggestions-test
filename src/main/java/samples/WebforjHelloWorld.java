package samples;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.App;
import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.addons.components.suggestionedit.events.ModifiedEvent;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.text.Label;
import com.webforj.component.window.Frame;
import com.webforj.exceptions.WebforjException;

import java.util.List;


/**
 * A simple HelloWorld app.
 */
@InlineStyleSheet(/* css */"""
  .mainFrame {
    display: inline-grid;
    gap: 20px;
    margin: 20px;
    padding: 20px;
    border: 1px dashed;
    border-radius: 10px;
  }
""")
@AppTitle("webforJ Hello World")
public class WebforjHelloWorld extends App {
  String lastInput = "";

  FakeNames fakeNames = new FakeNames();
  Paragraph hello = new Paragraph("Hello World!");
  Button btn = new Button("Say Hello");
  private SuggestionEdit se;
  private Label lbl;
  private SuggestionEdit se2;

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    mainFrame.addClassName("mainFrame");

    App.getPage().addJavaScript("http://localhost:8888/files/dwc-addons/dwc-addons.esm.js",true,"type=module");


    lbl = new Label("dynamic suggestions:");
    mainFrame.add(lbl);
    se = new SuggestionEdit();
    mainFrame.add(se);
    se.addModifiedListener(this::onModified);

    mainFrame.add(new Label("fixed list of suggestions:"));
    se2 = new SuggestionEdit();
    mainFrame.add(se2);
    se2.setSuggestions(fakeNames.findMatching("mil"));
 }

  private void onModified(ModifiedEvent modifiedEvent) {
    String search = modifiedEvent.getDetail();
    lbl.setText("Search Term: " + search);
    if (!lastInput.equals(search) && search.length()>2) {
      final List<String> matching = fakeNames.findMatching(search);
      se.setSuggestions(matching);
    }
  }
}
