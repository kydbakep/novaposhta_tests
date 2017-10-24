package ua.novaposhta.test.api;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import ua.novaposhta.test.api.classes.RequestSender;
import ua.novaposhta.test.api.classes.ResponseParser;

import java.io.IOException;

public class APITest {

    public APITest() throws IOException, ParseException {
    }

    private RequestSender sender = new RequestSender();
    private ResponseParser parser = new ResponseParser();

    private String removeThresh(String string) {
        return string.
                replace("[", "").
                replace("]", "").
                replace("\"", "");
    }

    @Test // Создание адреса контрагента
    public void createCounterPartyAddress() throws ParseException, JSONException, IOException {
        System.out.println("Создание адреса контрагента (createCounterPartyAddress)");
        sender.sendRequest("corporate", "createCounterPartyAddress");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String warningCode = removeThresh(parser.getKey("warningCodes"));
            String description = parser.getKey("data", "Description");

            if (warning.length() > 0) {
                System.out.println(parser.getKey("data", "Description"));
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);
            } else {
                System.out.println("created: " + description);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание контактного лица контрагента
    public void createCounterPartyContactPerson() throws ParseException, JSONException, IOException {
        System.out.println("Создание контактного лица контрагента (createCounterPartyContactPerson)");
        sender.sendRequest("corporate", "createCounterPartyContactPerson");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String warningCode = removeThresh(parser.getKey("warningCodes"));
            String phone = removeThresh(parser.getKey("data", "Phones"));
            String description = parser.getKey("data", "Description");

            if (warning.length() > 0) {
                System.out.println(parser.getKey("data", "Description") + ": " + phone);
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);
            } else {
                System.out.println("created: " + description + ": " + phone);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание контрагента ЮР получателя
    public void createCounterPartyRecipientOrganisation() throws ParseException, JSONException, IOException {
        System.out.println("Создание контрагента ЮР получателя (createCounterPartyRecipientOrganisation)");
        sender.sendRequest("corporate", "createCounterPartyRecipientOrganisation");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String description = parser.getKey("data", "Description");

            if (warning.length() > 0) {
                warning = removeThresh(parser.getKey("warnings", "DuplicateValidate"));
                String warningCode = removeThresh(parser.getKey("warningCodes", "DuplicateValidate"));
                System.out.println(parser.getKey("data", "OwnershipFormDescription") + " " + parser.getKey("data", "Description"));
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);

            } else {
                System.out.println("created: " + description);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание контрагента ПО получателя
    public void createCounterPartyRecipientPerson() throws ParseException, JSONException, IOException {
        System.out.println("Создание контрагента ПО получателя (createCounterPartyRecipientPerson)");
        sender.sendRequest("corporate", "createCounterPartyRecipientPerson");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("data", "ContactPerson", "warnings"));
            String description = parser.getKey("data", "Description");
            String person = removeThresh(parser.getKey("data", "FirstName") + " " +
                    parser.getKey("data", "MiddleName") + " " +
                    parser.getKey("data", "LastName"));

            if (warning.length() > 0) {
                String warningCode = removeThresh(parser.getKey("warningCodes"));
                System.out.println(parser.getKey("data", "OwnershipFormDescription") +
                        parser.getKey("data", "Description") + ": " + person);
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);

            } else {
                System.out.println("created: " + description + " - " + person);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание ИД на адрес, строкой
    public void createInternetDocumentToAddress() throws ParseException, JSONException, IOException {
        System.out.println("Создание ИД на адрес, строкой (createInternetDocumentToAddress)");
        sender.sendRequest("corporate", "createInternetDocumentToAddress");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String warningCode = removeThresh(parser.getKey("warningCodes"));
            String docType = parser.getKey("data", "TypeDocument");
            String docNumber = parser.getKey("data", "IntDocNumber");
            String deliveryDate = removeThresh(parser.getKey("data", "EstimatedDeliveryDate"));

            if (warning.length() > 0) {
                System.out.println(docType + ": " + docNumber + " (EstimatedDeliveryDate: " + deliveryDate + ")");
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);
            } else {
                System.out.println("created: " + docType + ": " + docNumber + " (" + deliveryDate + ")");
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание ИД на адрес, старый способ
    public void createInternetDocumentToAddressOldWay() throws ParseException, JSONException, IOException {
        System.out.println("Создание ИД на адрес, старый способ (createInternetDocumentToAddressOldWay)");
        sender.sendRequest("corporate", "createInternetDocumentToAddressOldWay");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String warningCode = removeThresh(parser.getKey("warningCodes"));
            String docType = parser.getKey("data", "TypeDocument");
            String docNumber = parser.getKey("data", "IntDocNumber");
            String deliveryDate = removeThresh(parser.getKey("data", "EstimatedDeliveryDate"));

            if (warning.length() > 0) {
                System.out.println(docType + ": " + docNumber + " (EstimatedDeliveryDate: " + deliveryDate + ")");
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);
            } else {
                System.out.println("created: " + docType + ": " + docNumber + " (" + deliveryDate + ")");
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание ИД на отделение, строкой
    public void createInternetDocumentToDepartment() throws ParseException, JSONException, IOException {
        System.out.println("Создание ИД на отделение, строкой (createInternetDocumentToDepartment)");
        sender.sendRequest("corporate", "createInternetDocumentToDepartment");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String warningCode = removeThresh(parser.getKey("warningCodes"));
            String docType = parser.getKey("data", "TypeDocument");
            String docNumber = parser.getKey("data", "IntDocNumber");
            String deliveryDate = removeThresh(parser.getKey("data", "EstimatedDeliveryDate"));

            if (warning.length() > 0) {
                System.out.println(docType + ": " + docNumber + " (EstimatedDeliveryDate: " + deliveryDate + ")");
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);
            } else {
                System.out.println("created: " + docType + ": " + docNumber + " (" + deliveryDate + ")");
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание ИД на отделение, старый способ
    public void createInternetDocumentToDepartmentOldWay() throws ParseException, JSONException, IOException {
        System.out.println("Создание ИД на отделение, старый способ (createInternetDocumentToDepartmentOldWay)");
        sender.sendRequest("corporate", "createInternetDocumentToDepartmentOldWay");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String warningCode = removeThresh(parser.getKey("warningCodes"));
            String docType = parser.getKey("data", "TypeDocument");
            String docNumber = parser.getKey("data", "IntDocNumber");
            String deliveryDate = removeThresh(parser.getKey("data", "EstimatedDeliveryDate"));
            String tZone = removeThresh(parser.getKey("data", "TzoneName"));

            if (warning.length() > 0) {
                System.out.println(docType + ": " + docNumber + " (EstimatedDeliveryDate: " + deliveryDate + "; " + tZone + ")");
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);
            } else {
                System.out.println("created: " + docType + ": " + docNumber + " (" + deliveryDate + ")");
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Обновление документа
    public void documentRefresh() throws ParseException, JSONException, IOException {
        System.out.println("Обновление документа (documentRefresh)");
        sender.sendRequest("corporate", "documentRefresh");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String warningCode = removeThresh(parser.getKey("warningCodes"));
            String docType = parser.getKey("data", "TypeDocument");
            String docNumber = parser.getKey("data", "IntDocNumber");
            String deliveryDate = removeThresh(parser.getKey("data", "EstimatedDeliveryDate"));
            String tZone = removeThresh(parser.getKey("data", "TzoneName"));

            if (warning.length() > 0) {
                System.out.println(docType + ": " + docNumber + " (EstimatedDeliveryDate: " + deliveryDate + "; " + tZone + ")");
                System.out.print("Warning ");
                System.out.println(warningCode + ": " + warning);
            } else {
                System.out.println("created: " + docType + ": " + docNumber + " (" + deliveryDate + ")");
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание реестра
    public void createRegister() throws ParseException, JSONException, IOException {
        System.out.println("Создание реестра (createRegister)");
        sender.sendRequest("corporate", "createRegister");
        parser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String date = removeThresh(parser.getKey("data", "Date"));
            parser.setNode(parser.getKey("data", "Data", "Warnings"));
            String number = parser.getKey("Number");
            String scansheet = parser.getKey("ScanSheetNumber");
            if (parser.getKey("Warning").length() > 0) {
                System.out.println("Warning: " + parser.getKey("Warning") + " (" + number + "; ScanSheet: " + scansheet + "), " + date);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Создание третьего лица
    public void createThirdParty() throws ParseException, JSONException, IOException {
        System.out.println("Создание третьего лица (createThirdParty)");
        sender.sendRequest("corporate", "createThirdParty");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String ownershipForm = removeThresh(parser.getKey("data", "OwnershipFormDescription"));
            String edrpou = removeThresh(parser.getKey("data", "EDRPOU"));
            String description = removeThresh(parser.getKey("data", "Description"));
            String warning = removeThresh(parser.getKey("warnings"));

            if (warning.length() > 0) {
                System.out.println(ownershipForm + " " + "'" + description + "'" + " EDRPOU: " + edrpou);
                String warningCode = removeThresh(parser.getKey("warningCodes"));
                System.out.println("Warning" + warningCode + ": " + warning);
            } else {
                System.out.println(ownershipForm + " " + "'" + description + "'" + " EDRPOU: " + edrpou);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test //  Создание ИД с плательщиком третьим лицом(третье лицо может оплачивать только по безналу)
    public void createInternetDocumentWithThirdPartyPayer() throws ParseException, JSONException, IOException {
        System.out.println("Создание ИД с плательщиком третьим лицом(третье лицо может оплачивать только по безналу) (createInternetDocumentWithThirdPartyPayer)");
        sender.sendRequest("corporate", "createInternetDocumentWithThirdPartyPayer");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String typeDocument = removeThresh(parser.getKey("data", "TypeDocument"));
            String docNumber = removeThresh(parser.getKey("data", "IntDocNumber"));
            String deliveryDate = removeThresh(parser.getKey("data", "EstimatedDeliveryDate"));
            String warning = removeThresh(parser.getKey("warnings"));

            if (warning.length() > 0) {
                System.out.println(typeDocument + " " + "'" + docNumber + "'" + " DeliveryDate " + deliveryDate);
                String warningCode = removeThresh(parser.getKey("warningCodes"));
                System.out.println("Warning " + warningCode + ": " + warning);
            } else {
                System.out.println(typeDocument + " " + "'" + docNumber + "'" + " DeliveryDate " + deliveryDate);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Получение справочника населенных пунктов (Сравнение первых НП на странице)
    public void getSettlements() throws ParseException, JSONException, IOException, InstantiationException {
        System.out.println("Получение справочника населенных пунктов (Сравнение первых НП на странице) (getSettlementsPageOne, getSettlementsPageTwo)");

        String settl_01;
        String settl_02;

        sender.sendRequest("general", "getSettlementsPageOne");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String settlementType = removeThresh(parser.getKey("data", "SettlementTypeDescription"));
            String settlementName = removeThresh(parser.getKey("data", "Description"));
            String area = removeThresh(parser.getKey("data", "AreaDescription"));
            String region = removeThresh(parser.getKey("data", "RegionsDescription"));
            String index = removeThresh(parser.getKey("data", "Index2"));
            String settlement = index + ", " + settlementType + " " + "'" + settlementName + "'" + " " + " " + area + ", " + region;
            settl_01 = settlement;

            String warning = removeThresh(parser.getKey("warnings"));

            if (warning.length() > 0) {
                System.out.println(settlement);
                String warningCode = removeThresh(parser.getKey("warningCodes"));
                System.out.println("Warning " + warningCode + ": " + warning);
            } else {
                System.out.println("first settlement on page: " + settlement);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }

        sender.sendRequest("general", "getSettlementsPageTwo");

        if (parser.getKey("success").equals("true")) {
            String warning = removeThresh(parser.getKey("warnings"));
            String settlementType = removeThresh(parser.getKey("data", "SettlementTypeDescription"));
            String settlementName = removeThresh(parser.getKey("data", "Description"));
            String area = removeThresh(parser.getKey("data", "AreaDescription"));
            String region = removeThresh(parser.getKey("data", "RegionsDescription"));
            String index = removeThresh(parser.getKey("data", "Index2"));
            String settlement = index + ", " + settlementType + " " + "'" + settlementName + "'" + " " + " " + area + ", " + region;
            settl_02 = settlement;

            if (warning.length() > 0) {
                System.out.println(settlement);
                String warningCode = removeThresh(parser.getKey("warningCodes"));
                System.out.println("Warning " + warningCode + ": " + warning);
            } else {
                System.out.println("first settlement on page: " + settlement);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }

        if (settl_01.equals(settl_02)) {
            throw new InstantiationException("Settlements should be different:" + settl_01);
        }
        System.out.println();
    }

    @Test // Получение данных конкретного населенного пункта
    public void getSettlement() throws ParseException, JSONException, IOException {
        System.out.println("Получение данных конкретного населенного пункта (getSettlement)");
        sender.sendRequest("general", "getSettlement");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            String settlementType = removeThresh(parser.getKey("data", "SettlementTypeDescription"));
            String settlementName = removeThresh(parser.getKey("data", "Description"));
            String area = removeThresh(parser.getKey("data", "AreaDescription"));
            String region = removeThresh(parser.getKey("data", "RegionsDescription"));
            String index = removeThresh(parser.getKey("data", "Index2"));
            String settlement = index + ", " + settlementType + " " + "'" + settlementName + "'" + " " + " " + area + ", " + region;

            String warning = removeThresh(parser.getKey("warnings"));

            if (warning.length() > 0) {
                System.out.println(settlement);
                String warningCode = removeThresh(parser.getKey("warningCodes"));
                System.out.println("Warning " + warningCode + ": " + warning);
            } else {
                System.out.println(settlement);
            }

        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Получение справочника областей для населенных пунктов
    public void getRegions() throws ParseException, JSONException, IOException {
        System.out.println("Получение справочника областей для населенных пунктов (getRegions)");
        sender.sendRequest("general", "getRegions");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            System.out.println(parser.getList("data", "Description"));
        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Получение справочника регионов для населенных пунктов
    public void getPlaces() throws ParseException, JSONException, IOException {
        System.out.println("Получение справочника регионов для населенных пунктов (getPlaces)");
        sender.sendRequest("general", "getPlaces");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));

        if (parser.getKey("success").equals("true")) {
            System.out.println(parser.getList("data", "Description"));
        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes", "AreaRef"));
            String error = removeThresh(parser.getKey("errors", "AreaRef"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Получение справочника отделений
    public void getOffice() throws ParseException, JSONException, IOException {
        System.out.println("Получение справочника отделений (getOffices)");
        sender.sendRequest("general", "getOffices");
//        analyser.printResponse();
        System.out.println("success: " + parser.getKey("success"));
        if (parser.getKey("success").equals("true")) {
            System.out.println(parser.getKey("data", "SettlementTypeDescription") + " "
                    + parser.getKey("data", "Description") + " ("
                    + parser.getKey("data", "DescriptionRu") + ")");
        } else {
            String errorCode = removeThresh(parser.getKey("errorCodes"));
            String error = removeThresh(parser.getKey("errors"));
            throw new IllegalArgumentException(errorCode + ": " + error);
        }
        System.out.println();
    }

    @Test // Генерація API ключа
    public void generateApiKey() throws IOException, ParseException, JSONException {
        System.out.println("Генерація API ключа (generateApiKey)");
        RequestSender sender = new RequestSender();
        sender.sendRequest("loyalty", "generateApiKey");
//        analyser.printResponse();
        ResponseParser parser = new ResponseParser();
        parser.setNode(parser.getKey("data", "ApiKey"));
        System.out.println("API key for loyal user: " + parser.getKey());

        sender.sendRequest("corporate", "generateApiKey");
        parser.setNode(parser.getKey("data", "ApiKey"));
        System.out.println("API key for corporate user: " + parser.getKey());

        System.out.println();
    }

//    @Test // Отримання API ключа
//    public void getApiKey() throws ParseException, JSONException, IOException {
//        System.out.println("Отримання API ключа");
//        Generator generator = new Generator();
//        System.out.println(generator.getKey("loyalty"));
//        System.out.println(generator.getKey("corporate"));
//
//        System.out.println();
//    }
}
