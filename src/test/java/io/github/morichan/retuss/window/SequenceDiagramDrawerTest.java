package io.github.morichan.retuss.window;

import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.language.uml.Package;
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import javafx.scene.control.TabPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SequenceDiagramDrawerTest {

    SequenceDiagramDrawer obj = new SequenceDiagramDrawer();

    @Nested
    class シーケンス図タブ内のタブに関して {

        Package umlPackage;

        @BeforeEach
        void setup() {
            umlPackage = new Package();
        }

        @Disabled
        @Test
        void シーケンス図タブを追加する() {
            int expected = 1;
            Class umlClass = new Class("Class");
            umlClass.addOperation(new OperationGraphic(new Operation(new Name("print"))));
            umlPackage.addClass(umlClass);

            obj.setUmlPackage(umlPackage);
            TabPane actual = obj.getTabPaneInSequenceTab();

            assertThat(actual.getTabs().size()).isEqualTo(expected);
        }
    }
}