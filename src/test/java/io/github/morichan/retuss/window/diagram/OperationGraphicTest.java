package io.github.morichan.retuss.window.diagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OperationGraphicTest {
    OperationGraphic operation;
    List<OperationGraphic> operations = new ArrayList<>();

    @BeforeEach
    void setUp() {
        operation = new OperationGraphic();

        operations.add(new OperationGraphic());
        operations.add(new OperationGraphic());
        operations.add(new OperationGraphic());
    }

    @Test
    void 操作を追加するとその操作を返す() {
        String expected = "operation()";

        operation.setText(expected);

        assertThat(operation.getText()).isEqualTo(expected);
    }

    @Test
    void 操作の表示を真にすると真を返す() {
        boolean expected = true;

        operation.setIndication(expected);

        assertThat(operation.isIndicate()).isEqualTo(expected);
    }

    @Test
    void 操作のリストに名前を追加するとその名前のリストを返す() {
        List<String> expected = Arrays.asList("operation1()", "operation2()", "operation3()");

        for (int i = 0; i < expected.size(); i++)
            operations.get(i).setText(expected.get(i));

        for (int i = 0; i < expected.size(); i++)
            assertThat(operations.get(i).getText()).isEqualTo(expected.get(i));
    }

    @Test
    void 操作のリストに真偽を追加するとその真偽を返す() {
        List<Boolean> expected = Arrays.asList(true, false, false);

        for (int i = 0; i < expected.size(); i++)
            operations.get(i).setIndication(expected.get(i));

        for (int i = 0; i < expected.size(); i++)
            assertThat(operations.get(i).isIndicate()).isEqualTo(expected.get(i));
    }
}