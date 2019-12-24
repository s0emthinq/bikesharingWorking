package by.me.bikesharing.command.user;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.CardService;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.CardValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;


/**
 * The type Make deposit command.
 */
public class MakeDepositCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();
    private CardService cardService = new CardService();
    private static final String ATTR_NAME_MESSAGE = "message";
    private static final String PARAM_NAME_SERIAL_NUMBER = "serial_number";
    private static final String PARAM_NAME_AMOUNT = "amount";
    private TextManager textManager;

    @Override
    public Router execute(HttpServletRequest request) {

        String serialNumber = request.getParameter(PARAM_NAME_SERIAL_NUMBER);
        String amountAsString = request.getParameter(PARAM_NAME_AMOUNT);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);

        String message = null;

        boolean flag = true;

        while (flag) {

            if (CardValidator.isParametersNullOrEmpty(serialNumber, amountAsString)) {
                message = textManager.getProperty("message.parameters.empty");
                break;
            }
            if (!CardValidator.isSerialNumberValid(serialNumber) || !CardValidator.isCardExist(serialNumber)) {
                message = textManager.getProperty("message.parameters.invalidSerialNumber");
                break;
            }
            if (!CardValidator.isAmountValid(amountAsString)) {
                message = textManager.getProperty("message.parameters.invalidAmount");
                break;
            }

            try {
                cardService.depositMoneyOnCard(serialNumber, new BigDecimal(amountAsString));
                message = textManager.getProperty("message.moneyDeposited");
            } catch (ServiceException e) {
                logger.info("Can't deposit money on card", e);
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }
            break;
        }
        request.setAttribute(ATTR_NAME_MESSAGE, message);

        return CommandEnum.FORWARD_TO_MAKE_DEPOSIT.getCommand().execute(request);
    }
}
