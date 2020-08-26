package club.javafamily.runner.common.table.filter;

import java.util.Map;

public interface ExportFilter {

   boolean accept(Map<String, Object> params);
}
