package org.clm.demo.mvc.primiary.serviceinterface;

import org.clm.demo.mvc.primiary.entity.Standard;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IStandService {
    public void addStandard(@RequestBody Standard standard);
    public void delStandard(@PathVariable String id, @PathVariable String version);
    public void updateStandard(@RequestBody Standard standard);
    public Standard findStandardByIdVersion(@PathVariable String id,@PathVariable String version);
    public List<Standard> findStandardByIdName(@PathVariable String id, @RequestBody String version);
}
