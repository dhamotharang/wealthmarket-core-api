package com.wm.core.controller;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.transaction.*;
import com.wm.core.service.transaction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("${app.title}")
public class TransactionController {

    @Autowired
    AccountingEntryService accountingEntryService;

    @Autowired
    TransactionChargeService transactionChargeService;

    @Autowired
    TransactionParameterService transactionParameterService;

    @Autowired
    TransactionParameterTypeService transactionParameterTypeService;

    @Autowired
    TransactionPartyService transactionPartyService;

    @Autowired
    TransactionTypeService transactionTypeService;


    //---------------Accounting Entry starts-------------------//

    @GetMapping(value = "/transactions/accountingentry/getall")
    ResponseEntity<?> getAllAccountingEntries() {
        BaseResponse response = accountingEntryService.getAllAccountingEntries();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/transactions/accountingentry/get/{id}")
    ResponseEntity<?> getAccountingEntryById(@PathVariable long id) {
        BaseResponse response = accountingEntryService.getAccountingEntryById(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/transactions/accountingentry/create")
    ResponseEntity<?> createNewAccountingEntry(@RequestAttribute("userId") long userId, @Valid @RequestBody AccountingEntry accountingEntry,
                                               @RequestBody TransactionType transactionType) {
        BaseResponse response = accountingEntryService.createNewAccountingEntry(accountingEntry, transactionType, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/transactions/accountingentry/edit")
    ResponseEntity<?> editAccountingEntry(@RequestAttribute("userId") long userId, @RequestBody AccountingEntry accountingEntry) {
        BaseResponse response = accountingEntryService.editAccountingEntry(accountingEntry, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/transactions/accountingentry/delete/{id}")
    ResponseEntity<?> deleteAccountingEntry(@PathVariable long id) {
        BaseResponse response = accountingEntryService.deleteAccountingEntry(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //---------------Accounting Entry ends-------------------//


    //---------------Transaction Charge starts-------------------//

    @GetMapping(value = "/transactions/transactioncharge/getall")
    ResponseEntity<?> getAllTransactionCharges() {
        BaseResponse response = transactionChargeService.getAllTransactionCharges();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/transactions/transactioncharge/get/{id}")
    ResponseEntity<?> getTransactionChargeById(@PathVariable long id) {
        BaseResponse response = transactionChargeService.getTransactionChargeById(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/transactions/transactioncharge/create")
    ResponseEntity<?> createNewTransactionCharge(@RequestAttribute("userId") long userId, @RequestBody TransactionCharge transactionCharge) {
        BaseResponse response = transactionChargeService.createNewTransactionCharge(transactionCharge, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/transactions/transactioncharge/edit")
    ResponseEntity<?> editTransactionCharge(@RequestAttribute("userId") long userId, @RequestBody TransactionCharge transactionCharge) {
        BaseResponse response = transactionChargeService.editTransactionCharge(transactionCharge, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/transactions/transactioncharge/delete/{id}")
    ResponseEntity<?> deleteTransactionCharge(@PathVariable long id) {
        BaseResponse response = transactionChargeService.deleteTransactionCharge(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //---------------Transaction Charge ends-------------------//


    //---------------Transaction Parameter starts-------------------//

    @GetMapping(value = "/transactions/transactionparameter/getall")
    ResponseEntity<?> getAllTransactionParameters() {
        BaseResponse response = transactionParameterService.getAllTransactionParameters();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/transactions/transactionparameter/get/{id}")
    ResponseEntity<?> getTransactionParameterById(@PathVariable long id) {
        BaseResponse response = transactionParameterService.getTransactionParameterById(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/transactions/transactionparameter/create")
    ResponseEntity<?> createNewTransactionParameter(@RequestAttribute("userId") long userId, @RequestBody TransactionParameter transactionParameter) {
        BaseResponse response = transactionParameterService.createNewTransactionParameter(transactionParameter, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/transactions/transactionparameter/edit")
    ResponseEntity<?> editTransactionParameter(@RequestAttribute("userId") long userId, @RequestBody TransactionParameter transactionParameter) {
        BaseResponse response = transactionParameterService.editTransactionParameter(transactionParameter, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/transactions/transactionparameter/delete/{id}")
    ResponseEntity<?> deleteTransactionParameter(@PathVariable long id) {
        BaseResponse response = transactionParameterService.deleteTransactionParameter(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //---------------Transaction Parameter ends-------------------//


    //---------------Transaction Parameter Type starts-------------------//

    @GetMapping(value = "/transactions/transactionparametertype/getall")
    ResponseEntity<?> getAllTransactionParameterTypes() {
        BaseResponse response = transactionParameterTypeService.getAllTransactionParameterTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/transactions/transactionparametertype/get/{id}")
    ResponseEntity<?> getTransactionParameterTypeById(@PathVariable long id) {
        BaseResponse response = transactionParameterTypeService.getTransactionParameterTypeById(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/transactions/transactionparametertype/create")
    ResponseEntity<?> createNewTransactionParameterType(@RequestAttribute("userId") long userId, @RequestBody
            TransactionParameterType transactionParameterType) {
        BaseResponse response = transactionParameterTypeService.createNewTransactionParameterType(transactionParameterType, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/transactions/transactionparametertype/edit")
    ResponseEntity<?> editTransactionParameterType(@RequestAttribute("userId") long userId, @RequestBody
            TransactionParameterType transactionParameterType) {
        BaseResponse response = transactionParameterTypeService.editTransactionParameterType(transactionParameterType, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/transactions/transactionparametertype/delete/{id}")
    ResponseEntity<?> deleteTransactionParameterType(@PathVariable long id) {
        BaseResponse response = transactionParameterTypeService.deleteTransactionParameterType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //---------------Transaction Parameter Type ends-------------------//


    //---------------Transaction Party starts-------------------//

    @GetMapping(value = "/transactions/transactionparty/getall")
    ResponseEntity<?> getAllTransactionParties() {
        BaseResponse response = transactionPartyService.getAllTransactionParties();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/transactions/transactionparty/get/{id}")
    ResponseEntity<?> getTransactionPartyById(@PathVariable long id) {
        BaseResponse response = transactionPartyService.getTransactionPartyById(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/transactions/transactionparty/create")
    ResponseEntity<?> createNewTransactionParty(@RequestAttribute("userId") long userId, @RequestBody TransactionParty transactionParty) {
        BaseResponse response = transactionPartyService.createNewTransactionParty(transactionParty, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/transactions/transactionparty/edit")
    ResponseEntity<?> editTransactionParty(@RequestAttribute("userId") long userId, @RequestBody TransactionParty transactionParty) {
        BaseResponse response = transactionPartyService.editTransactionParty(transactionParty, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/transactions/transactionparty/delete/{id}")
    ResponseEntity<?> deleteTransactionParty(@PathVariable long id) {
        BaseResponse response = transactionPartyService.deleteTransactionParty(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //---------------Transaction Party ends-------------------//


    //---------------Transaction Type starts-------------------//

    @GetMapping(value = "/transactions/transactiontype/getall")
    ResponseEntity<?> getAllTransactionTypes() {
        BaseResponse response = transactionTypeService.getAllTransactionTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/transactions/transactiontype/get/{id}")
    ResponseEntity<?> getTransactionTypeById(@PathVariable long id) {
        BaseResponse response = transactionTypeService.getTransactionTypeById(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/transactions/transactiontype/create")
    ResponseEntity<?> createNewTransactionType(@RequestAttribute("userId") long userId, @RequestBody TransactionType transactionType) {
        BaseResponse response = transactionTypeService.createNewTransactionType(transactionType, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/transactions/transactiontype/edit")
    ResponseEntity<?> editTransactionType(@RequestAttribute("userId") long userId, @RequestBody TransactionType transactionType) {
        BaseResponse response = transactionTypeService.editTransactionType(transactionType, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/transactions/transactiontype/delete/{id}")
    ResponseEntity<?> deleteTransactionType(@PathVariable long id) {
        BaseResponse response = transactionTypeService.deleteTransactionType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //---------------Transaction Type ends-------------------//
}