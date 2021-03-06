/**
 * Copyright (C) 2014-2015 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.core.data.manager.offline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.linkedin.pinot.core.data.manager.config.TableDataManagerConfig;
import com.linkedin.pinot.core.data.manager.realtime.RealtimeTableDataManager;


/**
 * Provide static function to get PartitionDataManager implementation.
 *
 */
public class TableDataManagerProvider {

  private static Map<String, Class<? extends TableDataManager>> keyToFunction =
      new ConcurrentHashMap<String, Class<? extends TableDataManager>>();

  static {
    keyToFunction.put("offline", OfflineTableDataManager.class);
    keyToFunction.put("realtime", RealtimeTableDataManager.class);
  }

  public static TableDataManager getTableDataManager(TableDataManagerConfig tableDataManagerConfig) {
    try {
      Class<? extends TableDataManager> cls =
          keyToFunction.get(tableDataManagerConfig.getTableDataManagerType().toLowerCase());
      if (cls != null) {
        TableDataManager tableDataManager = (TableDataManager) cls.newInstance();
        tableDataManager.init(tableDataManagerConfig);
        return tableDataManager;
      } else {
        throw new UnsupportedOperationException("No tableDataManager type found.");
      }
    } catch (Exception ex) {
      throw new RuntimeException("Not support tableDataManager type with - "
          + tableDataManagerConfig.getTableDataManagerType(), ex);
    }
  }

}
