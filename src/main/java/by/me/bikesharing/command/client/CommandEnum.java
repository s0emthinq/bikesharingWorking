package by.me.bikesharing.command.client;


import by.me.bikesharing.command.*;
import by.me.bikesharing.command.admin.*;
import by.me.bikesharing.command.common.*;
import by.me.bikesharing.command.user.*;

/**
 * The enum Command enum.
 */
public enum CommandEnum {

    /**
     * The Login.
     */
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    /**
     * The Logout.
     */
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    /**
     * The Show all bikes.
     */
    SHOW_ALL_BIKES {
        {
            this.command = new ShowAllBikesCommand();
        }
    },
    /**
     * The Forward to update bike.
     */
    FORWARD_TO_UPDATE_BIKE {
        {
            this.command = new ForwardToUpdateBikeFormCommand();
        }
    },
    /**
     * The Update bike.
     */
    UPDATE_BIKE {
        {
            this.command = new UpdateBikeCommand();
        }
    },
    /**
     * The Forward to add bike.
     */
    FORWARD_TO_ADD_BIKE {
        {
            this.command = new ForwardToAddBikeFormCommand();
        }
    },
    /**
     * The Add bike.
     */
    ADD_BIKE {
        {
            this.command = new AddBikeCommand();
        }
    },
    /**
     * The Order bike.
     */
    ORDER_BIKE {
        {
            this.command = new OrderBikeCommand();
        }
    },
    /**
     * The Forward to order bike.
     */
    FORWARD_TO_ORDER_BIKE {
        {
            this.command = new ForwardToOrderBikeCommand();
        }
    },
    /**
     * The Delete bike.
     */
    DELETE_BIKE {
        {
            this.command = new DeleteBikeCommand();
        }
    },
    /**
     * The Show all user orders.
     */
    SHOW_ALL_USER_ORDERS {
        {
            this.command = new ShowAllUsersOrders();
        }
    },
    /**
     * The Change language.
     */
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }

    },
    /**
     * The Finish order.
     */
    FINISH_ORDER {
        {
            this.command = new FinishOrderCommand();
        }
    },
    /**
     * The Forward to make deposit.
     */
    FORWARD_TO_MAKE_DEPOSIT {
        {
            this.command = new ForwardToMakeDepositCommand();
        }
    },
    /**
     * The Make deposit.
     */
    MAKE_DEPOSIT {
        {
            this.command = new MakeDepositCommand();
        }
    },
    /**
     * The Register.
     */
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    /**
     * The Forward to login.
     */
    FORWARD_TO_LOGIN {
        {
            this.command = new ForwardToLogin();
        }
    },
    /**
     * The Forward to register.
     */
    FORWARD_TO_REGISTER {
        {
            this.command = new ForwardToRegisterCommand();
        }
    },
    /**
     * The Show all users.
     */
    SHOW_ALL_USERS {
        {
            this.command = new ShowAllUsersCommand();
        }
    },
    /**
     * The Block user.
     */
    BLOCK_USER {
        {
            this.command = new BlockUserCommand();
        }
    },
    /**
     * The Unblock user.
     */
    UNBLOCK_USER {
        {
            this.command = new UnblockUserCommand();
        }
    },
    /**
     * The Forward to link card.
     */
    FORWARD_TO_LINK_CARD {
        {
            this.command = new ForwardToLinkCardCommand();
        }
    },
    /**
     * The Link card.
     */
    LINK_CARD {
        {
            this.command = new LinkCardCommand();
        }
    };

    /**
     * The Command.
     */
    ActionCommand command;

    /**
     * Gets command.
     *
     * @return the command
     */
    public ActionCommand getCommand() {
        return command;
    }
}