package org.clm.demo.mvc.primiary.serviceinterface;

import org.clm.demo.mvc.primiary.entity.Standard;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IStandService {
    void addStandard(@RequestBody Standard standard);
    void delStandard(@PathVariable String id, @PathVariable String version);
    Standard updateStandard(@RequestBody Standard standard);
    Standard findStandardByIdVersion(@PathVariable String id,@PathVariable String version);
    List<Standard> findStandardByIdName(@PathVariable String id, @RequestBody String version);
    List<Standard> getAllStandard();
}
