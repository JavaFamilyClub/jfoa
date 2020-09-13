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

/**
 * Meta-data used to build the search index map for the annotated component.
 */
export interface SearchableDescriptor {
   /**
    * The route to the annotated component, including the anchor within the enclosing
    * page. For example, '/settings/general#license'.
    */
   route: string;

   /**
    * The component title for display in the search results. These should be
    * localization <b>key</b> strings. For example,
    *    title: "em.clusterMonitoring.title"
    */
   title: string;

   /**
    * Search keywords to be associated with the annotated component. These should be
    * localization <b>key</b> strings. For example,
    * [ 'em.license.keyword1', 'em.license.keyword2' ].
    */
   keywords: string[];
}

/**
 * Decorator used to associate search metadata with components. This decorator is not
 * actually used at runtime, but instead is used to statically build a search index map
 * during the build.
 */
export function Searchable(descriptor: SearchableDescriptor) {
   // NO-OP
   return (target: any) => target;
}
