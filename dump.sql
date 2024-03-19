-- H2 2.2.224; 
SET DB_CLOSE_DELAY -1;         
;              
CREATE USER IF NOT EXISTS "SA" SALT 'ffcf356064b04575' HASH 'df9517a92e85fd98422efe4b1fb9dc10bb64a67c2750f88bd883375e54c7ee7e' ADMIN;          
CREATE MEMORY TABLE "PUBLIC"."metric"(
    "value" FLOAT(53) NOT NULL,
    "last_update_time" TIMESTAMP(6),
    "description" CHARACTER VARYING(255),
    "id" CHARACTER VARYING(255) NOT NULL,
    "name" CHARACTER VARYING(255),
    "source" CHARACTER VARYING(255),
    "type" CHARACTER VARYING(255)
);   
ALTER TABLE "PUBLIC"."metric" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("id");        
-- 38 +/- SELECT COUNT(*) FROM PUBLIC.metric;  
INSERT INTO "PUBLIC"."metric" VALUES
(6.437500269385055E-5, NULL, 'A summary of the pause duration of garbage collection cycles.', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0";', 'go_gc_duration_seconds', 'http://localhost:2112/metrics', 'summary'),
(1.813749986467883E-4, NULL, '', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0.25";', 'go_gc_duration_seconds', 'http://localhost:2112/metrics', ''),
(2.587919880170375E-4, NULL, '', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0.5";', 'go_gc_duration_seconds', 'http://localhost:2112/metrics', ''),
(4.081660008523613E-4, NULL, '', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0.75";', 'go_gc_duration_seconds', 'http://localhost:2112/metrics', ''),
(0.0025686249136924744, NULL, '', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"1";', 'go_gc_duration_seconds', 'http://localhost:2112/metrics', ''),
(0.01687704212963581, NULL, '', 'go_gc_duration_seconds_sum#http://localhost:2112/metrics#', 'go_gc_duration_seconds_sum', 'http://localhost:2112/metrics', ''),
(48.0, NULL, '', 'go_gc_duration_seconds_count#http://localhost:2112/metrics#', 'go_gc_duration_seconds_count', 'http://localhost:2112/metrics', ''),
(7.0, NULL, 'Number of goroutines that currently exist.', 'go_goroutines#http://localhost:2112/metrics#', 'go_goroutines', 'http://localhost:2112/metrics', 'gauge'),
(1.0, NULL, 'Information about the Go environment.', 'go_info#http://localhost:2112/metrics#version,"go1.21.6";', 'go_info', 'http://localhost:2112/metrics', 'gauge'),
(677472.0, NULL, 'Number of bytes allocated and still in use.', 'go_memstats_alloc_bytes#http://localhost:2112/metrics#', 'go_memstats_alloc_bytes', 'http://localhost:2112/metrics', 'gauge'),
(1.14655488E8, NULL, 'Total number of bytes allocated, even if freed.', 'go_memstats_alloc_bytes_total#http://localhost:2112/metrics#', 'go_memstats_alloc_bytes_total', 'http://localhost:2112/metrics', 'counter'),
(4223.0, NULL, 'Number of bytes used by the profiling bucket hash table.', 'go_memstats_buck_hash_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_buck_hash_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(671154.0, NULL, 'Total number of frees.', 'go_memstats_frees_total#http://localhost:2112/metrics#', 'go_memstats_frees_total', 'http://localhost:2112/metrics', 'counter'),
(3681872.0, NULL, 'Number of bytes used for garbage collection system metadata.', 'go_memstats_gc_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_gc_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(677472.0, NULL, 'Number of heap bytes allocated and still in use.', 'go_memstats_heap_alloc_bytes#http://localhost:2112/metrics#', 'go_memstats_heap_alloc_bytes', 'http://localhost:2112/metrics', 'gauge'),
(5152768.0, NULL, 'Number of heap bytes waiting to be used.', 'go_memstats_heap_idle_bytes#http://localhost:2112/metrics#', 'go_memstats_heap_idle_bytes', 'http://localhost:2112/metrics', 'gauge'),
(2514944.0, NULL, 'Number of heap bytes that are in use.', 'go_memstats_heap_inuse_bytes#http://localhost:2112/metrics#', 'go_memstats_heap_inuse_bytes', 'http://localhost:2112/metrics', 'gauge'),
(1839.0, NULL, 'Number of allocated objects.', 'go_memstats_heap_objects#http://localhost:2112/metrics#', 'go_memstats_heap_objects', 'http://localhost:2112/metrics', 'gauge'),
(5152768.0, NULL, 'Number of heap bytes released to OS.', 'go_memstats_heap_released_bytes#http://localhost:2112/metrics#', 'go_memstats_heap_released_bytes', 'http://localhost:2112/metrics', 'gauge'),
(7667712.0, NULL, 'Number of heap bytes obtained from system.', 'go_memstats_heap_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_heap_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(1.710885248E9, NULL, 'Number of seconds since 1970 of last garbage collection.', 'go_memstats_last_gc_time_seconds#http://localhost:2112/metrics#', 'go_memstats_last_gc_time_seconds', 'http://localhost:2112/metrics', 'gauge'),
(0.0, NULL, 'Total number of pointer lookups.', 'go_memstats_lookups_total#http://localhost:2112/metrics#', 'go_memstats_lookups_total', 'http://localhost:2112/metrics', 'counter');               
INSERT INTO "PUBLIC"."metric" VALUES
(672993.0, NULL, 'Total number of mallocs.', 'go_memstats_mallocs_total#http://localhost:2112/metrics#', 'go_memstats_mallocs_total', 'http://localhost:2112/metrics', 'counter'),
(12000.0, NULL, 'Number of bytes in use by mcache structures.', 'go_memstats_mcache_inuse_bytes#http://localhost:2112/metrics#', 'go_memstats_mcache_inuse_bytes', 'http://localhost:2112/metrics', 'gauge'),
(15600.0, NULL, 'Number of bytes used for mcache structures obtained from system.', 'go_memstats_mcache_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_mcache_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(104160.0, NULL, 'Number of bytes in use by mspan structures.', 'go_memstats_mspan_inuse_bytes#http://localhost:2112/metrics#', 'go_memstats_mspan_inuse_bytes', 'http://localhost:2112/metrics', 'gauge'),
(130368.0, NULL, 'Number of bytes used for mspan structures obtained from system.', 'go_memstats_mspan_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_mspan_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(4194304.0, NULL, 'Number of heap bytes when next garbage collection will take place.', 'go_memstats_next_gc_bytes#http://localhost:2112/metrics#', 'go_memstats_next_gc_bytes', 'http://localhost:2112/metrics', 'gauge'),
(1123089.0, NULL, 'Number of bytes used for other system allocations.', 'go_memstats_other_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_other_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(720896.0, NULL, 'Number of bytes in use by the stack allocator.', 'go_memstats_stack_inuse_bytes#http://localhost:2112/metrics#', 'go_memstats_stack_inuse_bytes', 'http://localhost:2112/metrics', 'gauge'),
(720896.0, NULL, 'Number of bytes obtained from system for stack allocator.', 'go_memstats_stack_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_stack_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(1.334376E7, NULL, 'Number of bytes obtained from system.', 'go_memstats_sys_bytes#http://localhost:2112/metrics#', 'go_memstats_sys_bytes', 'http://localhost:2112/metrics', 'gauge'),
(9.0, NULL, 'Number of OS threads created.', 'go_threads#http://localhost:2112/metrics#', 'go_threads', 'http://localhost:2112/metrics', 'gauge'),
(1378.0, NULL, 'The total number of processed events', 'myapp_processed_ops_total#http://localhost:2112/metrics#', 'myapp_processed_ops_total', 'http://localhost:2112/metrics', 'counter'),
(1.0, NULL, 'Current number of scrapes being served.', 'promhttp_metric_handler_requests_in_flight#http://localhost:2112/metrics#', 'promhttp_metric_handler_requests_in_flight', 'http://localhost:2112/metrics', 'gauge'),
(1572.0, NULL, 'Total number of scrapes by HTTP status code.', 'promhttp_metric_handler_requests_total#http://localhost:2112/metrics#code,"200";', 'promhttp_metric_handler_requests_total', 'http://localhost:2112/metrics', 'counter'),
(0.0, NULL, '', 'promhttp_metric_handler_requests_total#http://localhost:2112/metrics#code,"500";', 'promhttp_metric_handler_requests_total', 'http://localhost:2112/metrics', ''),
(0.0, NULL, '', 'promhttp_metric_handler_requests_total#http://localhost:2112/metrics#code,"503";', 'promhttp_metric_handler_requests_total', 'http://localhost:2112/metrics', '');            
CREATE MEMORY TABLE "PUBLIC"."metric_labels"(
    "labels" CHARACTER VARYING(255),
    "labels_key" CHARACTER VARYING(255) NOT NULL,
    "metric_id" CHARACTER VARYING(255) NOT NULL
);        
ALTER TABLE "PUBLIC"."metric_labels" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" PRIMARY KEY("labels_key", "metric_id");            
-- 9 +/- SELECT COUNT(*) FROM PUBLIC.metric_labels;            
INSERT INTO "PUBLIC"."metric_labels" VALUES
('"0"', 'quantile', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0";'),
('"0.25"', 'quantile', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0.25";'),
('"0.5"', 'quantile', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0.5";'),
('"0.75"', 'quantile', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"0.75";'),
('"1"', 'quantile', 'go_gc_duration_seconds#http://localhost:2112/metrics#quantile,"1";'),
('"go1.21.6"', 'version', 'go_info#http://localhost:2112/metrics#version,"go1.21.6";'),
('"200"', 'code', 'promhttp_metric_handler_requests_total#http://localhost:2112/metrics#code,"200";'),
('"500"', 'code', 'promhttp_metric_handler_requests_total#http://localhost:2112/metrics#code,"500";'),
('"503"', 'code', 'promhttp_metric_handler_requests_total#http://localhost:2112/metrics#code,"503";');
ALTER TABLE "PUBLIC"."metric_labels" ADD CONSTRAINT "PUBLIC"."FKkb3hdd4k6dn6yq364ei36pwqe" FOREIGN KEY("metric_id") REFERENCES "PUBLIC"."metric"("id") NOCHECK;
