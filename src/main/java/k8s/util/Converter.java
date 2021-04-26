package k8s.util;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.File;
import java.util.Map;

public class Converter {
    public static void main(String[] args) {
        String projectId = "tjs-gcloud-playground";
        String targetLanguage = "ko";
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        FileHook outputFileHook = new FileHook((File file) -> {
            return true;
        }, (File file) -> {
            return new File(file.getPath() + "_out");
        });

        FieldHook translateDescriptionFieldHook = new FieldHook((String key, Object val) -> {
            return val instanceof String && key.equals("description");
        }, (Map.Entry<String, Object> entry) -> {
            String text = (String) entry.getValue();
            Translation translation = translate.translate(text, Translate.TranslateOption.sourceLanguage("en"),
                    Translate.TranslateOption.targetLanguage("ko"));
            entry.setValue(translation.getTranslatedText());
        });


        ResourceConverter converter = new ResourceConverter()
                .addFieldHook(translateDescriptionFieldHook);

        try {
            Resource resource = new Resource("sample/registry-operator/notary.yaml");
            resource = converter.convert(resource);
            resource.writeTo(new File("sample/registry-operator/notary-result.yaml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

