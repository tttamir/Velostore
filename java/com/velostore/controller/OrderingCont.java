package com.velostore.controller;

import com.velostore.controller.main.Attributes;
import com.velostore.model.Ordering;
import com.velostore.model.enums.OrderingStatus;
import com.velostore.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordering")
public class OrderingCont extends Attributes {

    @GetMapping
    public String Ordering(Model model) {
        addAttributes(model);
        model.addAttribute("buyers", userRepo.findAllByRole(Role.BUYER));
        return "ordering";
    }

    @PostMapping
    public String OrderingBuyer(Model model, @RequestParam Long idBuyer) {
        addAttributesOrdering(model, idBuyer);
        return "ordering";
    }

    @GetMapping("/confirmed/{idOrdering}")
    public String OrderingConfirmed(Model model, @PathVariable Long idOrdering) {
        Ordering ordering = orderingRepo.getReferenceById(idOrdering);
        ordering.setOrderingStatus(OrderingStatus.CONFIRMED);
        orderingRepo.save(ordering);
        addAttributesOrdering(model, ordering.getUser().getId());
        return "ordering";
    }

    @GetMapping("/confirmed/all/{idBuyer}")
    public String OrderingConfirmedAll(Model model, @PathVariable Long idBuyer) {
        List<Ordering> orderingList = userRepo.getReferenceById(idBuyer).getOrderingList();
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.NOT_CONFIRMED) {
                i.setOrderingStatus(OrderingStatus.CONFIRMED);
            }
        }
        orderingRepo.saveAll(orderingList);
        addAttributesOrdering(model, idBuyer);
        return "ordering";
    }

    @GetMapping("/shipped/all/{idBuyer}")
    public String OrderingShippedAll(Model model, @PathVariable Long idBuyer) {
        List<Ordering> orderingList = userRepo.getReferenceById(idBuyer).getOrderingList();
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.CONFIRMED) {
                i.setOrderingStatus(OrderingStatus.SHIPPED);
            }
        }
        orderingRepo.saveAll(orderingList);
        addAttributesOrdering(model, idBuyer);
        return "ordering";
    }

    @GetMapping("/shipped/{idOrdering}")
    public String OrderingShipped(Model model, @PathVariable Long idOrdering) {
        Ordering ordering = orderingRepo.getReferenceById(idOrdering);
        ordering.setOrderingStatus(OrderingStatus.SHIPPED);
        orderingRepo.save(ordering);
        addAttributesOrdering(model, ordering.getUser().getId());
        return "ordering";
    }

}
