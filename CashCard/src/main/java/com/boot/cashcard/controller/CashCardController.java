package com.boot.cashcard.controller;


import com.boot.cashcard.repo.CashCardRepository;
import com.boot.cashcard.model.CashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import java.security.Principal;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    public final CashCardRepository repo;

    public CashCardController(CashCardRepository repo) {
        this.repo = repo;
    }

    /*// with hard-coded values
    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        if (requestedId.equals(99L)) {
            CashCard cashCard = new CashCard(99L, 123.45);
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/{requestedId}")
//    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) { //without security check
//        Optional<CashCard> cashCardOptional = repo.findById(requestedId);//without security check
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) { //principal.getName() will return the username provided from Basic Auth.
        Optional<CashCard> cashCardOptional = Optional.ofNullable(repo.findByIdAndOwner(requestedId, principal.getName()));
        if (cashCardOptional.isPresent()) {//&& cashCardOptional.get().getId()==1
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            return ResponseEntity.notFound().build();//this is actual one as per lab
//            return new ResponseEntity("No Data Found!", HttpStatus.NOT_FOUND); //enhanced code
        }
    }

    @PostMapping//exact owner-user only creates cashcard now
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        CashCard cashCardWithOwner = new CashCard(newCashCardRequest.getId(), newCashCardRequest.getAmount(), principal.getName());
        CashCard savedCashCard = repo.save(cashCardWithOwner);
  /*  @PostMapping //with this code any owner can create cashcard
    public ResponseEntity<CashCard> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = repo.save(newCashCardRequest);*/
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.getId())
                .toUri();
//        return ResponseEntity.created(locationOfNewCashCard).body(savedCashCard);//enhanced code
        return ResponseEntity.created(locationOfNewCashCard).build(); //is the actual code as per lab
    }

//    @GetMapping() //return all records from db
//    private ResponseEntity<Iterable<CashCard>> findAll() {
//        return ResponseEntity.ok(repo.findAll());
//    }



    @GetMapping//return records based on pagination with security, only owner can get his details and others not allowed
       /* //return records based on pagination
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable) {
        Page<CashCard> page = repo.findAll(*/
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = repo.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
//                        pageable.getSort()//prefer the order how the db returns but by default is ascending order
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))//the getSortOr() method provides default values for the page, size, and sort parameters
                ));
        return ResponseEntity.ok(page.getContent());
    }


}
