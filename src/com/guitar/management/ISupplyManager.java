package com.guitar.management;

import com.guitar.models.Supply;
import java.util.List;

public interface ISupplyManager {
    void addSupply(Supply supply);
    void updateSupply(Supply supply);
    void deleteSupply(int supplyId);
    Supply getSupply(int supplyId);
    List<Supply> getAllSupplies();
    
}
