/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.common.service;

import club.javafamily.commons.lens.ExportTableLens;
import club.javafamily.commons.enums.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public interface Exporter {

   boolean isAccept(ExportType exportType);

   void export(ExportTableLens tableLens,
               OutputStream out,
               ExportType exportType)
      throws Exception;
}
