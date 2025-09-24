package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.Miscellaneous;
import com.rv.band_manager.Model.MiscellaneousLoan;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Repository.MiscellaneousRepository;
import com.rv.band_manager.Repository.MiscellaneousLoanRepository;
import com.rv.band_manager.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MiscellaneousService interface.
 * Provides logic for managing miscellaneous items, including CRUD operations.
 */
@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {

    private final MiscellaneousRepository miscellaneousRepository;
    private final MiscellaneousLoanRepository miscellaneousLoanRepository;
    /**
     * Constructs a new instance of MiscellaneousServiceImpl with the specified repository.
     *
     * @param miscellaneousRepository the repository used for miscellaneous item data access
     */
    public MiscellaneousServiceImpl(MiscellaneousRepository miscellaneousRepository,
        MiscellaneousLoanRepository miscellaneousLoanRepository) {
        this.miscellaneousRepository = miscellaneousRepository;
        this.miscellaneousLoanRepository = miscellaneousLoanRepository;
    }

    /**
     * Retrieves all miscellaneous items from the repository.
     *
     * @return a list of all miscellaneous items
     */
    public List<Miscellaneous> getAllMiscellaneous() {
        return miscellaneousRepository.findAll();
    }

    public void setAvailableMiscellaneousQuantity(){
      List<Miscellaneous> items = miscellaneousRepository.findAll();
      for (Miscellaneous item : items) {
          Integer loaned = miscellaneousLoanRepository.sumLoanedQuantityByMiscellaneousId(item.getId());
          if (loaned == null) loaned = 0;
          item.setAvailableQuantity(item.getQuantity() - loaned);
      }
    }

    /**
     * Saves a new miscellaneous item to the repository.
     *
     * @param item the miscellaneous item to be saved
     * @return the saved miscellaneous item
     */
    public Miscellaneous saveMiscellaneous(Miscellaneous item) {
        return miscellaneousRepository.save(item);
    }

    /**
     * Retrieves a miscellaneous item by its unique identifier.
     *
     * @param id the unique ID of the miscellaneous item
     * @return an Optional containing the miscellaneous item if found, or empty if not
     */
    public Optional<Miscellaneous> getMiscellaneousById(Long id){return miscellaneousRepository.findById(id);}

    public Optional<Miscellaneous> getMiscellaneousByNameAndMake(String name, String make){
      return miscellaneousRepository.findByNameAndMake(name, make);
    }

    /**
     * Updates an existing miscellaneous item in the repository.
     *
     * @param id the ID of the miscellaneous item to update
     * @param updatedItem the new miscellaneous item data to update with
     * @return the updated miscellaneous item
     * @throws IllegalArgumentException if the miscellaneous item is not found
     */
    public Miscellaneous updateMiscellaneous(Long id, Miscellaneous updatedItem) {
        Miscellaneous item = miscellaneousRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Miscellaneous item not found"));

        // Update the item's attributes with the new values from the updated item
        item.setName(updatedItem.getName());
        item.setMake(updatedItem.getMake());
        item.setQuantity(updatedItem.getQuantity());
        item.setSpecificForInstrument(updatedItem.getSpecificForInstrument());
        return miscellaneousRepository.save(item);
    }

    /**
     * Deletes an existing miscellaneous item from the repository.
     *
     * @param id the ID of the miscellaneous item to delete
     * @throws IllegalArgumentException if the miscellaneous item is not found
     */
    public void deleteMiscellaneous(Long id) {
        Miscellaneous item = miscellaneousRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Miscellaneous item not found"));
        miscellaneousRepository.delete(item);
    }

}
