package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BOX_DESC_BOX1;
import static seedu.address.logic.commands.CommandTestUtil.BOX_DESC_BOX2;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BOX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOX_BOX1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOX_BOX2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB)
                .withRemark(VALID_ORDER_DESCRIPTION_BOB)
                .withTags(VALID_TAG_FRIEND)
                .withBoxes(VALID_BOX_BOX1)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + ORDER_DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND + BOX_DESC_BOX1,
                new AddCommand(expectedPerson));

        // multiple tags and boxes
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withRemark(VALID_ORDER_DESCRIPTION_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withBoxes(VALID_BOX_BOX1, VALID_BOX_BOX2)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ORDER_DESCRIPTION_DESC_BOB
                        + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + BOX_DESC_BOX1 + BOX_DESC_BOX2,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BOX_DESC_BOX1 + ORDER_DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND;

        // repeated name, phone, email, address, remark
        assertParseFailure(
                parser,
                NAME_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        assertParseFailure(
                parser,
                PHONE_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
        assertParseFailure(
                parser,
                EMAIL_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
        assertParseFailure(
                parser,
                ADDRESS_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
        assertParseFailure(
                parser,
                ORDER_DESCRIPTION_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARKS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + BOX_DESC_BOX1 + ORDER_DESCRIPTION_DESC_AMY,
                new AddCommand(new PersonBuilder(AMY)
                        .withRemark(VALID_ORDER_DESCRIPTION_AMY)
                        .withTags()
                        .withBoxes(VALID_BOX_BOX1).build()));

        // zero tags and no remark provided
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + BOX_DESC_BOX1,
                new AddCommand(new PersonBuilder(AMY)
                        .withRemark(Remark.DEFAULT_REMARK)
                        .withTags()
                        .withBoxes(VALID_BOX_BOX1).build()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORDER_DESCRIPTION_DESC_BOB, expectedMessage);
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORDER_DESCRIPTION_DESC_BOB, expectedMessage);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + ORDER_DESCRIPTION_DESC_BOB, expectedMessage);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + ORDER_DESCRIPTION_DESC_BOB, expectedMessage);
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_ORDER_DESCRIPTION_BOB + VALID_EXPIRY_DATE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + ORDER_DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + BOX_DESC_BOX1,
                Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + ORDER_DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + BOX_DESC_BOX1,
                Phone.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + ORDER_DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + BOX_DESC_BOX1,
                Email.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + ORDER_DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + BOX_DESC_BOX1,
                Address.getValidationMessage(INVALID_ADDRESS_DESC));

        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INVALID_ORDER_DESCRIPTION_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + BOX_DESC_BOX1,
                Remark.MESSAGE_INVALID_CHARACTERS);

        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + ORDER_DESCRIPTION_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND + BOX_DESC_BOX1,
                Tag.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + ORDER_DESCRIPTION_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND + INVALID_BOX_DESC,
                Box.MESSAGE_INVALID_BOX_WITH_EXPIRY_FORMAT);

        // first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + ORDER_DESCRIPTION_DESC_BOB + BOX_DESC_BOX1, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + ORDER_DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND + BOX_DESC_BOX1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
