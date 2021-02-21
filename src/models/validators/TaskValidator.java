package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Task;

public class TaskValidator {
    // バリデーションを実行する（今後拡張した時ようにエラー内容はlistで格納）
    public static List<String> validate(Task task) {
        List<String> errors = new ArrayList<String>();

        String content_error = validateContent(task.getContent());
        if(!content_error.equals("")) {
            errors.add(content_error);
        }

        return errors;

    }

    // タスク内容のバリデーションチェック
    private static String validateContent(String content) {
        if(content == null || content.equals("")) {
            return "タスク内容を入力してください";
        }

        return "";
    }

}
