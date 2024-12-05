package co.edu.cue.inventario.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MensajeUtil {
    private final MessageSource messageSource;

    @Autowired
    public MensajeUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMensaje(String codigo, Object... parametros) {
        return messageSource.getMessage(
                codigo,
                parametros,
                LocaleContextHolder.getLocale()
        );
    }
}
