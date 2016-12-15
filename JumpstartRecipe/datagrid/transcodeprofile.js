define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Name',
    'Transcoder URI',
    'Transcoder Profile',
    'Transcoder Workflow'
  ]);
 
  return {
    type: 'transcodeProfile',
    columns: [
      {
        label: t('Name'),
        field: 'name',
        renderer: function(context) {
          // wrap the title with a link to inspect the entity
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Transcoder URI'),
        field: 'transcoderUri',
        renderer: function(context) {
          // wrap the title with a link to inspect the entity
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: false,
        filterable: false,
        width: '200px'
      },
      {
        label: t('Transcoder Profile'),
        field: 'transcoderProfile',
        renderer: function(context) {
          // wrap the title with a link to inspect the entity
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
          label: t('Transcoder Workflow'),
          field: 'transcoderWorkflow',
          renderer: function(context) {
            context.value = irdeto.control.DataGrid.cellRenderers.dataProviderAware(context);
            context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
            return context.value;
          },
          sortable: true,
          filterable: true,
          width: '200px'
        }
    ],
    sort: [
      { field: 'name', direction: 'asc' }
    ]
  };
});
