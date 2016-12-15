<?php
/**
 * @package    Irdeto\Entity
 * @subpackage Entity\Recipe\Jumpstart
 * @copyright  Copyright (c) 2009-2012 Irdeto (http://www.irdeto.com)
 */

namespace Irdeto\Entity\Recipe\Jumpstart;

use Irdeto\Entity\Model\Type\Attribute\DefinitionFactory;
use Irdeto\Entity\Model\Type\Attribute\Provider\Country as CountryProvider;
use Irdeto\Entity\Model\Type\Attribute\Type\Currency;
use Irdeto\Entity\Model\Type\Attribute\Type\Type;
use Irdeto\Entity\Model\Type\Relationship;
use Irdeto\Entity\Recipe\AbstractRecipe;
use Irdeto\Entity\Validate\Validator;
use Irdeto\Entity\Model\Type\Attribute\Locale;
use MediaConsole\Widget\EpgVisualization\Widget as EpgWidget;

/**
 * Services Jumpstart recipe.
 *
 * @package    Irdeto\Entity
 * @subpackage Entity\Recipe\Jumpstart
 * @copyright  Copyright (c) 2009-2012 Irdeto (http://www.irdeto.com)
 */
class Jumpstart extends AbstractRecipe
{
    const FLAG_CREATE_METADATA_VIEW = 'createMetadataView';
    const FLAG_CREATE_QA_VIEW = 'createQaView';    // known as the QA Metadata View
    const FLAG_CREATE_GENERAL_TASK_VIEW = 'createGeneralTaskView';
    const FLAG_CREATE_QA_CONTENT_VIEW = 'createQaContentView';

    /**
     * @var string
     */
    protected $name = 'Jumpstart';

    /**
     * @var string
     */
    protected $description = 'Services Jumpstart recipe';

    /**
     * @var array
     */
    protected $locales = array('en_US');

    /**
     * {@inheritdoc}
     */
    protected function configure()
    {
        Currency::addCountries(array(
            'US' => array(
                'en_US' => 'United States'
            )
        ));
        Currency::addCurrencies(array(
            'USD' => array(
                'en_US' => 'US Dollar'
            )
        ));
        Currency::setCountryCurrencies('US', array('USD'));
        Currency::addCountries(array(
            'NL' => array(
                'en_US' => 'Netherlands'
            )
        ));
        Currency::addCurrencies(array(
            'EUR' => array(
                'en_US' => 'Euro'
            )
        ));
        Currency::setCountryCurrencies('NL', array('EUR'));
        Currency::addCountries(array(
            'GB' => array(
                'en_US' => 'United Kingdom'
            )
        ));
        Currency::addCurrencies(array(
            'GBP' => array(
                'en_US' => 'Pound Sterling'
            )
        ));
        Currency::setCountryCurrencies('GB', array('GBP'));
        Currency::addCountries(array(
            '01' => array(
                'en_US' => 'World'
            )
        ));
        Currency::setCountryCurrencies('01', array('USD'));
        Currency::persistConfigToSettings();
    }

    /**
     * {@inheritdoc}
     */
    protected function createTypes()
    {
        $baseEntity = $this->createType('baseEntity');

        $base = $this->createType('base', $baseEntity);
        $base->setDefinitions(array(
            DefinitionFactory::create('uriId', 'ShortText', array(
                'indexed' => true,
                'labels' => array(
                    'en_US' => 'URI ID'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 1024))
                )
            )),
            DefinitionFactory::create('startDateTime', 'Date', array(
                'labels' => array(
                    'en_US' => 'Start Date/Time'
                ),
            )),
            DefinitionFactory::create('endDateTime', 'Date', array(
                'labels' => array(
                    'en_US' => 'End Date/Time'
                ),
            )),
            DefinitionFactory::create('provider', 'Text', array(
                'labels' => array(
                    'en_US' => 'Provider'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('lastPublishDateTime', 'Date', array(
                'labels' => array(
                    'en_US' => 'Last Publish Date/Time'
                ),
                'required' => false,
                'disabled' => true,
                'isLocalized' => false
            )),
            DefinitionFactory::create('lastModifiedDateTime', 'Date', array(
                'labels' => array(
                    'en_US' => 'Last Modified Date/Time'
                ),
                'required' => false,
                'disabled' => true,
                'isLocalized' => false
            ))
        ));

        $baseMetadata = $this->createType('baseMetadata', $base);
        $baseMetadata->setDefinitions(array(
            DefinitionFactory::create('metadataApproved', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Metadata Approved?'
                ),
                'disabled' => true
            )),
            DefinitionFactory::create('dataMaster', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Data Master'
                ),
                'locales' => array(
                    new Locale('en_US', array('defaultValue' => 'Media Manager'))
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'Ingest' => 'Ingest',
                        'Media Manager' => 'Media Manager',
                    )
                )
            ))
        ));

        $baseMetadataWithTitle = $this->createType('baseMetadataWithTitle', $baseMetadata);
        $baseMetadataWithTitle->setDefinitions(array(
            DefinitionFactory::create('titleSortName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Title (Sort Name)'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 22))
                )
            )),
            DefinitionFactory::create('titleBrief', 'Text', array(
                'labels' => array(
                    'en_US' => 'Title (Brief)'
                ),
                'entityLabel' => true,
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 19))
                )
            )),
            DefinitionFactory::create('titleMedium', 'Text', array(
                'labels' => array(
                    'en_US' => 'Title (Medium)'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 35))
                )
            )),
            DefinitionFactory::create('titleLong', 'Text', array(
                'labels' => array(
                    'en_US' => 'Title (Long)'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 128))
                )
            )),
            DefinitionFactory::create('summaryShort', 'Text', array(
                'formElementName' => Type::ELEMENT_TEXTAREA,
                'labels' => array(
                    'en_US' => 'Summary (Short)'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 256))
                )
            )),
            DefinitionFactory::create('summaryMedium', 'Text', array(
                'formElementName' => Type::ELEMENT_TEXTAREA,
                'labels' => array(
                    'en_US' => 'Summary (Medium)'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 1024))
                )
            )),
            DefinitionFactory::create('summaryLong', 'Text', array(
                'formElementName' => Type::ELEMENT_TEXTAREA,
                'labels' => array(
                    'en_US' => 'Summary (Long)'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 4096))
                )
            )),
            DefinitionFactory::create('countryOfOrigin', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Country of Origin'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => false,
                'providerName' => 'country', // ISO-3166-1
            )),
            DefinitionFactory::create('showType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Show Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'Series' => 'Series',
                        'Kids' => 'Kids',
                        'Movies' => 'Movies',
                        'Sports' => 'Sports',
                        'Music' => 'Music',
                        'Events' => 'Events',
                        'Ad' => 'Ad',
                        'Lifestyle' => 'Lifestyle',
                        'Commercial' => 'Commercial',
                        'Documentary' => 'Documentary',
                        'Educational' => 'Educational',
                        'Entertainment' => 'Entertainment',
                        'News' => 'News',
                        'Religious' => 'Religious',
                        'Others' => 'Others'
                    )
                )
            )),
            DefinitionFactory::create('keywords', 'Text', array(
                'labels' => array(
                    'en_US' => 'Keywords'
                ),
                'isLocalized' => false
            ))
        ));

        $baseMetadataWithContent = $this->createType('baseMetadataWithContent', $baseMetadataWithTitle);
        $baseMetadataWithContent->setDefinitions(array(
            DefinitionFactory::create('contentApproved', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Content Approved?'
                ),
                'disabled' => true
            ))
        ));

        $genre = $this->createType('genre', $baseMetadata);
        $genre->setOrg();
        $genre->setLabel('Genre', 'en_US');
        $genre->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $genre->setFlag(self::FLAG_CREATE_QA_VIEW);
        $genre->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $genre->setDefinitions(array(
            DefinitionFactory::create('title', 'Text', array(
                'labels' => array(
                    'en_US' => 'Title'
                ),
                'entityLabel' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 32))
                )
            )),
            DefinitionFactory::create('isDisplayGenre', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Visible in the UI?'
                ),
                'descriptions' => array(
                    'en_US' => 'This boolean indicates whether the genre should be published.'
                )
            )),
            DefinitionFactory::create('isEnabled', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Is genre enabled?'
                ),
                'descriptions' => array(
                    'en_US' => 'This boolean indicates whether the genre is enabled or not.'
                )
            )),
            DefinitionFactory::create('ingestGenre', 'ShortText', array(
                'indexed' => true,
                'formElementName' => Type::ELEMENT_TEXTAREA,
                'labels' => array(
                    'en_US' => 'Ingest Genre'
                ),
                'isLocalized' => false
            ))
        ));
        $genre->addEntityRelationship('brands', 'brand', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'genres'));
        $genre->getRelationship('brands')->setLabel('Brands', 'en_US');
        $genre->addEntityRelationship('seriess', 'series', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'genres'));
        $genre->getRelationship('seriess')->setLabel('Series', 'en_US');
        $genre->addEntityRelationship('subscriptionPackages', 'subscriptionPackage', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'genres'));
        $genre->getRelationship('subscriptionPackages')->setLabel('Subscription Packages', 'en_US');
        $genre->addEntityRelationship('tvodCollections', 'tvodCollection', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'genres'));
        $genre->getRelationship('tvodCollections')->setLabel('Collections', 'en_US');
        $genre->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'genres'));
        $genre->getRelationship('events')->setLabel('Events', 'en_US');

        $deviceProfile = $this->createType('deviceProfile', $baseEntity);
        $deviceProfile->setLabel('Device Profile', 'en_US');
        $deviceProfile->setDefinitions(array(
            DefinitionFactory::create('deviceClass', 'Text', array(
                'labels' => array(
                    'en_US' => 'Device Class'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => true
            )),
            DefinitionFactory::create('name', 'Text', array(
                'labels' => array(
                    'en_US' => 'Name'
                ),
                'entityLabel' => true,
                'isLocalized' => false,
                'required' => true,
                'unique' => false
            )),
            DefinitionFactory::create('enabled', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Enabled?'
                )
            )),
            DefinitionFactory::create('packagingType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Packaging Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => true,
                'unique' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'NA' => 'N/A',
                        'DASH' => 'DASH',
                        'HLS' => 'HTTP Live Streaming',
                        'Smooth Stream' => 'Smooth Stream'
                    )
                )
            ))
        ));
        $deviceProfile->addEntityRelationship('encodeProfiles', 'encodeProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'deviceProfiles'));
        $deviceProfile->getRelationship('encodeProfiles')->setLabel('Encode Profiles', 'en_US');
        $deviceProfile->addEntityRelationship('transcodeProfiles', 'transcodeProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'deviceProfiles'));
        $deviceProfile->getRelationship('transcodeProfiles')->setLabel('Transcode Profiles', 'en_US');
        $deviceProfile->addEntityRelationship('protectProfiles', 'protectProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'deviceProfiles'));
        $deviceProfile->getRelationship('protectProfiles')->setLabel('Protect Profiles', 'en_US');
        $deviceProfile->addEntityRelationship('termMappings', 'termMapping', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'deviceProfiles', 'inverse' => true));
        $deviceProfile->getRelationship('termMappings')->setLabel('Term Mappings', 'en_US');

        $baseProtection = $this->createType('baseProtection', $baseEntity);
        $baseProtection->setDefinitions(array(
            DefinitionFactory::create('name', 'Text', array(
                'labels' => array(
                    'en_US' => 'Name'
                ),
                'entityLabel' => true,
                'isLocalized' => false,
                'required' => true,
                'unique' => true
            )),
            DefinitionFactory::create('protectionType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Protection Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => true,
                'unique' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'ActiveCloak' => 'ActiveCloak via CPS',
                        'ActiveCloak2GoDRM' => 'ActiveCloak to Go via CPS',
                        'PlayReadyDRM' => 'PlayReady Smooth Streaming via CPS',
                        'HttpLiveStreamingDRM' => 'Http Live Streaming via CPS',
                        'IrdetoSKE' => 'Irdeto SKE via CPS',
                        'PlayReadyHlsDRM' => 'PlayReady HLS via CPS',
                        'PlayReadyUS' => 'PlayReady via Unified Streaming',
                        'SKEUS' => 'Irdeto SKE via Unified Streaming',
                        'PlayReadyWidevineMultiUS' => 'PlayReady & Widevine via Unified Streaming',
                        'WidevineUS' => 'Widevine via Unified Streaming',
                        'NoneUS' => 'No Encryption via Unified Streaming'
                    )
                )
            ))
        ));


        $policyGroupProfile = $this->createType('policyGroupProfile', $baseEntity);
        $policyGroupProfile->setLabel('Policy Group Profile', 'en_US');
        $policyGroupProfile->setDefinitions(array(
            DefinitionFactory::create('policyGroupName ', 'Text', array(
                'labels' => array(
                    'en_US' => 'Policy Group Name '
                ),
                'entityLabel' => true,
                'required' => true,
                'unique' => true,
                'isLocalized' => false
            )),
            DefinitionFactory::create('policyGroupDescription', 'Text', array(
                'labels' => array(
                    'en_US' => 'Policy Group Description  '
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('licenseProfileReference', 'Integer', array(
                'labels' => array(
                    'en_US' => 'License Profile Reference'
                ),
                'required' => true
            ))
        ));
        $policyGroupProfile->addEntityRelationship('policyProfiles', 'policyProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'policyGroupProfiles', 'inverse' => true));
        $policyGroupProfile->getRelationship('policyProfiles')->setLabel('Policy Profiles', 'en_US');
        $policyGroupProfile->addEntityRelationship('termMappings', 'termMapping', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'policyGroupProfiles', 'inverse' => true));
        $policyGroupProfile->getRelationship('termMappings')->setLabel('Term Mappings', 'en_US');

        $policyProfile = $this->createType('policyProfile', $baseEntity);
        $policyProfile->setLabel('Policy Profile', 'en_US');
        $policyProfile->setDefinitions(array(
            DefinitionFactory::create('policyName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Policy Name'
                ),
                'entityLabel' => true,
                'required' => true,
                'unique' => true,
                'isLocalized' => false
            )),
            DefinitionFactory::create('policyDescription', 'Text', array(
                'labels' => array(
                    'en_US' => 'Policy Description'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('policyType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Policy Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'Free' => 'Free',
                        'Subscription' => 'Subscription',
                        'Rental' => 'Rental',
                    )
                )
            )),
            DefinitionFactory::create('entitlementStartMode', 'Text', array(
                'labels' => array(
                    'en_US' => 'Entitlement Start Mode'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('entitlementDuration', 'Text', array(
                'labels' => array(
                    'en_US' => 'Entitlement Duration'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('RequireSubscription', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Require Subscription?'
                )
            )),
            DefinitionFactory::create('subscriptionReference', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Subscription Reference'
                )
            )),
            DefinitionFactory::create('licenseProfileReference', 'Integer', array(
                'labels' => array(
                    'en_US' => 'License Profile Reference'
                ),
                'required' => true
            )),
            DefinitionFactory::create('requireAuthentication', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Require Authentication?'
                )
            )),
            DefinitionFactory::create('priceTable', 'Currency', array(
                'labels' => array(
                    'en_US' => 'Price Table'
                )
            )),
            DefinitionFactory::create('subscriptionBillingInterval ', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Subscription Billing Interval '
                )
            )),
            DefinitionFactory::create('subscriptionGroupId', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Subscription GroupId'
                )
            )),
            DefinitionFactory::create('subscriptionId', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Subscription Id'
                )
            )),
            DefinitionFactory::create('subscriptionReleaseDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Subscription Release Date'
                )
            )),
            DefinitionFactory::create('subscriptionReleaseEnd', 'Date', array(
                'labels' => array(
                    'en_US' => 'Subscription Release End'
                )
            )),
            DefinitionFactory::create('subscriptionMinimumBillingInterval', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Subscription Minimum Billing Interval'
                )
            )),
            DefinitionFactory::create('subscriptionBillingEndDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Subscription Billing EndDate'
                )
            )),
            DefinitionFactory::create('numberOfDevices', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Number Of Devices'
                ),
                'required' => true
            )),
            DefinitionFactory::create('productTaxType', 'Text', array(
                'labels' => array(
                    'en_US' => 'Product Tax Type'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('billImmediately', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Bill Immediately?'
                )
            )),
            DefinitionFactory::create('syndicateAccountId  ', 'Text', array(
                'labels' => array(
                    'en_US' => 'Syndicate AccountId '
                ),
                'isLocalized' => false
            ))
        ));
        $policyProfile->addEntityRelationship('policyGroupProfiles', 'policyGroupProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'policyProfiles'));
        $policyProfile->getRelationship('policyGroupProfiles')->setLabel('Policy Group Profiles', 'en_US');
        $policyProfile->addEntityRelationship('termMappings', 'termMapping', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'policyProfiles', 'inverse' => true));
        $policyProfile->getRelationship('termMappings')->setLabel('Term Mappings', 'en_US');

        $encodeProfile = $this->createType('encodeProfile', $baseProtection);
        $encodeProfile->setLabel('Encode Profile', 'en_US');
        $encodeProfile->setDefinitions(array(
            DefinitionFactory::create('liveUri', 'Text', array(
                'labels' => array(
                    'en_US' => 'Encoder Live URI'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => false
            ))
        ));
        $encodeProfile->addEntityRelationship('deviceProfiles', 'deviceProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'encodeProfiles', 'inverse' => true));
        $encodeProfile->getRelationship('deviceProfiles')->setLabel('Device Profiles', 'en_US');
        $encodeProfile->addEntityRelationship('channels', 'channel', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'encodeProfiles', 'inverse' => true));
        $encodeProfile->getRelationship('channels')->setLabel('Channels', 'en_US');
        $encodeProfile->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'encodeProfiles', 'inverse' => true));
        $encodeProfile->getRelationship('events')->setLabel('Events', 'en_US');

        $transcodeProfile = $this->createType('transcodeProfile', $baseEntity);
        $transcodeProfile->setLabel('Transcode Profile', 'en_US');
        $transcodeProfile->setDefinitions(array(
            DefinitionFactory::create('name', 'Text', array(
                'labels' => array(
                    'en_US' => 'Name'
                ),
                'entityLabel' => true,
                'isLocalized' => false,
                'required' => true,
                'unique' => true
            )),
            DefinitionFactory::create('transcoderProfile', 'Text', array(
                'labels' => array(
                    'en_US' => 'Transcoder Profile'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => false
            )),
            DefinitionFactory::create('transcoderUri', 'Text', array(
                'labels' => array(
                    'en_US' => 'Transcoder URI'
                ),
                'isLocalized' => false,
                'required' => false,
                'unique' => false
            )),
            DefinitionFactory::create('transcodedFilePattern', 'Text', array(
                'formElementName' => Type::ELEMENT_TEXTAREA,
                'labels' => array(
                    'en_US' => 'Transcoded File Pattern'
                ),
                'isLocalized' => false,
                'required' => false,
                'unique' => false
            )),
            DefinitionFactory::create('transcodedFileCount', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Transcoded File Count'
                ),
                'descriptions' => array(
                    'en_US' => 'Transcoded Renditions File counter which are ready for Protection.'
                ),
                'required' => true,
                'unique' => false
            )),
            DefinitionFactory::create('transcoderWorkflow', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Transcoder Workflow'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => true,
                'unique' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'TranscodeElementalCloud' => 'Elemental Cloud'
                    )
                )
            ))
        ));
        $transcodeProfile->addEntityRelationship('deviceProfiles', 'deviceProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'transcodeProfiles', 'inverse' => true));
        $transcodeProfile->getRelationship('deviceProfiles')->setLabel('Device Profiles', 'en_US');
        $transcodeProfile->addEntityRelationship('transSubs', 'transVideoSub', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'transcodeProfiles', 'inverse' => true));
        $transcodeProfile->getRelationship('transSubs')->setLabel('Transcoded Video Subcontent', 'en_US');
        $transcodeProfile->addEntityRelationship('protectSubs', 'protectVideoSub', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'transcodeProfiles', 'inverse' => true));
        $transcodeProfile->getRelationship('protectSubs')->setLabel('Protected Video Subcontent', 'en_US');
        $transcodeProfile->addEntityRelationship('protectProfiles', 'protectProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'transcodeProfiles', 'inverse' => true));
        $transcodeProfile->getRelationship('protectProfiles')->setLabel('Protect Profiles', 'en_US');

        $protectProfile = $this->createType('protectProfile', $baseProtection);
        $protectProfile->setLabel('Protect Profile', 'en_US');
        $protectProfile->setDefinitions(array(
            DefinitionFactory::create('required', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Required?'
                )
            ))
        ));
        $protectProfile->addEntityRelationship('deviceProfiles', 'deviceProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'protectProfiles', 'inverse' => true));
        $protectProfile->getRelationship('deviceProfiles')->setLabel('Device Profiles', 'en_US');
        $protectProfile->addEntityRelationship('transcodeProfiles', 'transcodeProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'protectProfiles'));
        $protectProfile->getRelationship('transcodeProfiles')->setLabel('Transcode Profiles', 'en_US');
        $protectProfile->addEntityRelationship('protectSubs', 'protectVideoSub', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'protectProfiles', 'inverse' => true));
        $protectProfile->getRelationship('protectSubs')->setLabel('Protected Video Subcontent', 'en_US');

        $ratingScheme = $this->createType('ratingScheme', $baseEntity);
        $ratingScheme->setLabel('Rating Scheme', 'en_US');
        $ratingScheme->setDefinitions(array(
            DefinitionFactory::create('classification', 'Text', array(
                'labels' => array(
                    'en_US' => 'Classification System'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => true,
                'entityLabel' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 100))
                )
            )),
            //change the country of system, so that more than one country can be selected.
            DefinitionFactory::create('countryOfSystem', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Parental Control Slider Countries'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => false,
                'providerName' => 'country', // ISO-3166-1
            )),
            //change the country included, so that more than one country can be selected.
            DefinitionFactory::create('countriesIncluded', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Classification Countries'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => false,
                'providerName' => 'country', // ISO-3166-1
            ))
        ));

        $ratingScheme->addEntityRelationship('ratings', 'rating', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'ratingSchemes', 'inverse' => true));
        $ratingScheme->getRelationship('ratings')->setLabel('Ratings', 'en_US');

        $rating = $this->createType('rating', $base);
        $rating->setLabel('Rating', 'en_US');
        $rating->setDefinitions(array(
            DefinitionFactory::create('ratingLabel', 'Text', array(
                'labels' => array(
                    'en_US' => 'Rating'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'entityLabel' => true,
                'required' => true
            )),
            DefinitionFactory::create('minimumAge', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Minimum Age'
                ),
                'required' => true,
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            ))
        ));
        $rating->addEntityRelationship('seriess', 'series', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'ratings', 'inverse' => true));
        $rating->getRelationship('seriess')->setLabel('Series', 'en_US');
        $rating->addEntityRelationship('brands', 'brand', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'ratings', 'inverse' => true));
        $rating->getRelationship('brands')->setLabel('Brands', 'en_US');
        $rating->addEntityRelationship('ratingSchemes', 'ratingScheme', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'ratings'));
        $rating->getRelationship('ratingSchemes')->setLabel('Rating Schemes', 'en_US');
        $rating->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'ratings', 'inverse' => true));
        $rating->getRelationship('events')->setLabel('Events', 'en_US');

        $brand = $this->createType('brand', $baseMetadataWithContent);
        $brand->setLabel('Brand', 'en_US');
        $brand->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $brand->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $brand->setFlag(self::FLAG_CREATE_QA_VIEW);
        $brand->setFlag(self::FLAG_CREATE_GENERAL_TASK_VIEW);
        $brand->setFlag(self::FLAG_CREATE_QA_CONTENT_VIEW);
        $brand->setDefinitions(array(
            DefinitionFactory::create('seriesCount', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Series Count'
                ),
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            ))
        ));

        $brand->addEntityRelationship('seriess', 'series', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'brands', 'inverse' => true));
        $brand->getRelationship('seriess')->setLabel('Series', 'en_US');
        $brand->addEntityRelationship('offers', 'offer', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'brands'));
        $brand->getRelationship('offers')->setLabel('Offers', 'en_US');
        $brand->addEntityRelationship('subscriptionPackages', 'subscriptionPackage', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'brands'));
        $brand->getRelationship('subscriptionPackages')->setLabel('Subscription Packages', 'en_US');
        $brand->addEntityRelationship('tvodCollections', 'tvodCollection', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'brands'));
        $brand->getRelationship('tvodCollections')->setLabel('Collections', 'en_US');
        $brand->addEntityRelationship('imageContent', 'imageContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $brand->getRelationship('imageContent')->setLabel('Images', 'en_US');
        $brand->addEntityRelationship('genres', 'genre', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'brands', 'inverse' => true));
        $brand->getRelationship('genres')->setLabel('Genres', 'en_US');
        $brand->addEntityRelationship('ratings', 'rating', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'brands'));
        $brand->getRelationship('ratings')->setLabel('Rating', 'en_US');

        $series = $this->createType('series', $baseMetadataWithContent);
        $series->setLabel('Series', 'en_US');
        $series->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $series->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $series->setFlag(self::FLAG_CREATE_QA_VIEW);
        $series->setFlag(self::FLAG_CREATE_GENERAL_TASK_VIEW);
        $series->setFlag(self::FLAG_CREATE_QA_CONTENT_VIEW);
        $series->setDefinitions(array(
            DefinitionFactory::create('season', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Season'
                ),
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            )),
            DefinitionFactory::create('programCount', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Program Count'
                ),
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            ))
        ));

        $series->addEntityRelationship('programs', 'program', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'seriess', 'inverse' => true));
        $series->getRelationship('programs')->setLabel('Programs', 'en_US');
        $series->addEntityRelationship('brands', 'brand', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'seriess'));
        $series->getRelationship('brands')->setLabel('Brands', 'en_US');
        $series->addEntityRelationship('offers', 'offer', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'seriess'));
        $series->getRelationship('offers')->setLabel('Offers', 'en_US');
        $series->addEntityRelationship('subscriptionPackages', 'subscriptionPackage', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'seriess'));
        $series->getRelationship('subscriptionPackages')->setLabel('Subscription Packages', 'en_US');
        $series->addEntityRelationship('tvodCollections', 'tvodCollection', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'seriess'));
        $series->getRelationship('tvodCollections')->setLabel('Collections', 'en_US');
        $series->addEntityRelationship('genres', 'genre', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'seriess', 'inverse' => true));
        $series->getRelationship('genres')->setLabel('Genres', 'en_US');
        $series->addEntityRelationship('imageContent', 'imageContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $series->getRelationship('imageContent')->setLabel('Images', 'en_US');
        $series->addEntityRelationship('ratings', 'rating', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'seriess'));
        $series->getRelationship('ratings')->setLabel('Rating', 'en_US');

        $subscriptionPackage = $this->createType('subscriptionPackage', $baseMetadataWithContent);
        $subscriptionPackage->setLabel('Subscription Package', 'en_US');
        $subscriptionPackage->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $subscriptionPackage->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $subscriptionPackage->setFlag(self::FLAG_CREATE_QA_VIEW);
        $subscriptionPackage->setFlag(self::FLAG_CREATE_GENERAL_TASK_VIEW);
        $subscriptionPackage->setFlag(self::FLAG_CREATE_QA_CONTENT_VIEW);
        $subscriptionPackage->setDefinitions(array(
            DefinitionFactory::create('smsPackageId', 'Text', array(
                'labels' => array(
                    'en_US' => 'SMS Package ID'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('alacarte', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'A-la-carte?'
                )
            ))
        ));

        $subscriptionPackage->addEntityRelationship('channels', 'channel', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'subscriptionPackages', 'inverse' => true));
        $subscriptionPackage->getRelationship('channels')->setLabel('Channels', 'en_US');
        $subscriptionPackage->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'subscriptionPackages', 'inverse' => true));
        $subscriptionPackage->getRelationship('events')->setLabel('Events', 'en_US');
        $subscriptionPackage->addEntityRelationship('brands', 'brand', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'subscriptionPackages', 'inverse' => true));
        $subscriptionPackage->getRelationship('brands')->setLabel('Brands', 'en_US');
        $subscriptionPackage->addEntityRelationship('seriess', 'series', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'subscriptionPackages', 'inverse' => true));
        $subscriptionPackage->getRelationship('seriess')->setLabel('Series', 'en_US');
        $subscriptionPackage->addEntityRelationship('programs', 'program', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'subscriptionPackages', 'inverse' => true));
        $subscriptionPackage->getRelationship('programs')->setLabel('Programs', 'en_US');
        $subscriptionPackage->addEntityRelationship('offers', 'offer', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'subscriptionPackages'));
        $subscriptionPackage->getRelationship('offers')->setLabel('Offers', 'en_US');
        $subscriptionPackage->addEntityRelationship('imageContent', 'imageContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $subscriptionPackage->getRelationship('imageContent')->setLabel('Images', 'en_US');
        $subscriptionPackage->addEntityRelationship('genres', 'genre', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'subscriptionPackages', 'inverse' => true));
        $subscriptionPackage->getRelationship('genres')->setLabel('Genres', 'en_US');

        $tvodCollection = $this->createType('tvodCollection', $baseMetadataWithContent);
        $tvodCollection->setLabel('Collection', 'en_US');
        $tvodCollection->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $tvodCollection->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $tvodCollection->setFlag(self::FLAG_CREATE_QA_VIEW);
        $tvodCollection->setFlag(self::FLAG_CREATE_GENERAL_TASK_VIEW);
        $tvodCollection->setFlag(self::FLAG_CREATE_QA_CONTENT_VIEW);

        $tvodCollection->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'tvodCollections', 'inverse' => true));
        $tvodCollection->getRelationship('events')->setLabel('Events', 'en_US');
        $tvodCollection->addEntityRelationship('brands', 'brand', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'tvodCollections', 'inverse' => true));
        $tvodCollection->getRelationship('brands')->setLabel('Brands', 'en_US');
        $tvodCollection->addEntityRelationship('seriess', 'series', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'tvodCollections', 'inverse' => true));
        $tvodCollection->getRelationship('seriess')->setLabel('Series', 'en_US');
        $tvodCollection->addEntityRelationship('programs', 'program', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'tvodCollections', 'inverse' => true));
        $tvodCollection->getRelationship('programs')->setLabel('Programs', 'en_US');
        $tvodCollection->addEntityRelationship('offers', 'offer', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'tvodCollections'));
        $tvodCollection->getRelationship('offers')->setLabel('Offers', 'en_US');
        $tvodCollection->addEntityRelationship('imageContent', 'imageContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $tvodCollection->getRelationship('imageContent')->setLabel('Images', 'en_US');
        $tvodCollection->addEntityRelationship('genres', 'genre', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'tvodCollections', 'inverse' => true));
        $tvodCollection->getRelationship('genres')->setLabel('Genres', 'en_US');

        $program = $this->createType('program', $baseMetadataWithContent);
        $program->setLabel('Program', 'en_US');
        $program->setWorkflowDiagram('com.irdeto.jumpstart.workflow.Program', 'entityId');
        $program->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $program->setFlag(self::FLAG_CREATE_QA_VIEW);
        $program->setFlag(self::FLAG_CREATE_GENERAL_TASK_VIEW);
        $program->setFlag(self::FLAG_CREATE_QA_CONTENT_VIEW);
        $program->setDefinitions(array(
            DefinitionFactory::create('episodeName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Episode Name'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('episodeId', 'Text', array(
                'labels' => array(
                    'en_US' => 'Episode ID'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('NDSOfferID', 'Text', array(
                'labels' => array(
                    'en_US' => 'NDS Offer ID'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('isClosedCaptioning', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Closed Captioning?'
                ),
            )),
            DefinitionFactory::create('isDownloadable', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Downloadable?'
                ),
            )),
            DefinitionFactory::create('downloadExpiry', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Download Expiry in days'
                ),
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            )),
            DefinitionFactory::create('ndsPrice', 'Integer', array(
                'labels' => array(
                    'en_US' => 'NDS Price'
                ),
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            )),
            DefinitionFactory::create('maxconcurrentstream', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Maximum Concurrent Stream'
                ),
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            )),
            DefinitionFactory::create('MSORating', 'Integer', array(
                'labels' => array(
                    'en_US' => 'MSO Rating'
                ),
            )),
            DefinitionFactory::create('displayRunTime', 'RunTime', array(
                'labels' => array(
                    'en_US' => 'Display Run Time'
                ),
            )),
            DefinitionFactory::create('yearOfRelease', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Year of Release'
                ),
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 1900))
                )
            )),
            DefinitionFactory::create('genre', 'Text', array(
                'labels' => array(
                    'en_US' => 'Genre'
                ),
                'isLocalized' => true,
                'required' => false
            )),

            DefinitionFactory::create('rating', 'ShortText', array(
                'labels' => array(
                    'en_US' => 'Rating'
                ),
                'isLocalized' => false,
                'required' => false
            )),
            DefinitionFactory::create('isSeasonPremier', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Season Premier?'
                ),
            )),
            DefinitionFactory::create('isSeasonFinale', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Season Finale?'
                ),
            )),
            DefinitionFactory::create('linearBroadcastDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Last Broadcast Date'
                ),
            ))
        ));
        $program->addEntityRelationship('contributors', 'person', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $program->getRelationship('contributors')->setLabel('Contributors', 'en_US');
        $program->addEntityRelationship('subscriptionPackages', 'subscriptionPackage', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'programs'));
        $program->getRelationship('subscriptionPackages')->setLabel('Subscription Packages', 'en_US');
        $program->addEntityRelationship('tvodCollections', 'tvodCollection', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'programs'));
        $program->getRelationship('tvodCollections')->setLabel('Collections', 'en_US');
        $program->addEntityRelationship('scheduleSlots', 'scheduleSlot', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'programs', 'inverse' => true));
        $program->getRelationship('scheduleSlots')->setLabel('Schedule Slots', 'en_US');
        $program->addEntityRelationship('videoContent', 'videoContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $program->getRelationship('videoContent')->setLabel('Videos', 'en_US');
        $program->addEntityRelationship('imageContent', 'imageContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $program->getRelationship('imageContent')->setLabel('Images', 'en_US');
        $program->addEntityRelationship('seriess', 'series', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'programs'));
        $program->getRelationship('seriess')->setLabel('Series', 'en_US');
        $program->addEntityRelationship('offers', 'offer', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'programs'));
        $program->getRelationship('offers')->setLabel('Offers', 'en_US');
        $program->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'programs'));
        $program->getRelationship('events')->setLabel('Events', 'en_US');

        $person = $this->createType('person', $baseEntity);
        $person->setLabel('Person', 'en_US');
        $person->setDefinitions(array(
            DefinitionFactory::create('contribution', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Contribution'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'actor' => 'Actor',
                        'director' => 'Director',
                        'writer' => 'Writer',
                        'producer' => 'Producer'
                    )
                )
            )),
            DefinitionFactory::create('firstName', 'Text', array(
                'labels' => array(
                    'en_US' => 'First Name'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 16))
                )
            )),
            DefinitionFactory::create('lastName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Last Name'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 16))
                )
            )),
            DefinitionFactory::create('sortableName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Sortable Name'
                ),
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 32))
                )
            )),
            DefinitionFactory::create('fullName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Full Name'
                ),
                'entityLabel' => true,
                'isLocalized' => true,
                'validators' => array(
                    new Validator('StringLength', array('max' => 32))
                )
            )),
        ));

        $content = $this->createType('content', $base);
        $content->setDefinitions(array(
            DefinitionFactory::create('sourceUrl', 'Text', array(
                'labels' => array(
                    'en_US' => 'Source URL'
                ),
                'entityLabel' => true,
                'required' => false,
                'unique' => true,
                'descriptions' => array(
                    'en_US' => 'content.SourceURL from Cablelabs metadata.'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('fileSize', 'Text', array(
                'labels' => array(
                    'en_US' => 'File Size'
                ),
                'isLocalized' => false,
                'descriptions' => array(
                    'en_US' => 'content.ContentFileSize from Cablelabs metadata.'
                )
            )),
            DefinitionFactory::create('checkSum', 'Text', array(
                'labels' => array(
                    'en_US' => 'Check Sum'
                ),
                'isLocalized' => false,
                'descriptions' => array(
                    'en_US' => 'content.ContentCheckSum from Cablelabs metadata.'
                )
            )),
            DefinitionFactory::create('sourceVersion', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Source Version'
                ),
                'descriptions' => array(
                    'en_US' => 'The version increments by 1 whenever a media file is re-ingested.'
                )
            )),
            DefinitionFactory::create('publishVersion', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Publish Version'
                ),
                'descriptions' => array(
                    'en_US' => 'The version increments by 1 whenever a media file is re-published.'
                )
            )),
            DefinitionFactory::create('isPublished', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'This file is published.'
                ),
                'disabled' => true,
                'isLocalized' => false,
                'required' => false
            )),
            DefinitionFactory::create('publishedDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Published date'
                ),
                'disabled' => true,
                'required' => false
            ))
        ));

        $videoContent = $this->createType('videoContent', $content);
        $videoContent->setLabel('Video', 'en_US');
        $videoContent->setDefinitions(array(
            DefinitionFactory::create('contentType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Content Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'movie' => 'Movie',
                        'preview' => 'Preview',
                        'barker' => 'Barker'
                    )
                )
            )),
            DefinitionFactory::create('duration', 'Duration', array(
                'labels' => array(
                    'en_US' => 'Duration'
                ),
                'descriptions' => array(
                    'en_US' => 'Expected duration format of P(nY)(nM)(nD)T(nH)(nM)(nS). For example P4563Y2M13DT12H8M10S'
                ),
            )),
            DefinitionFactory::create('screenFormat', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Screen Format'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'Standard' => 'Standard',
                        'Widescreen' => 'Widescreen',
                        'Letterbox' => 'Letterbox',
                        'OAR' => 'OAR'
                    )
                )
            )),
            DefinitionFactory::create('language', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Language'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'language',
            )),
            DefinitionFactory::create('subtitleLanguage', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Subtitle Langauge'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'language',
            )),
            DefinitionFactory::create('dubbedLanguage', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Dubbed Language'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'language',
            ))
        ));
        $videoContent->addEntityRelationship('subcontent', 'sourceVideoSub', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $videoContent->getRelationship('subcontent')->setLabel('Source Video Subcontent', 'en_US');
        $videoContent->addEntityRelationship('subtitleContent', 'subtitleContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $videoContent->getRelationship('subtitleContent')->setLabel('Subtitle', 'en_US');

        $imageContent = $this->createType('imageContent', $content);
        $imageContent->setLabel('Image', 'en_US');
        $imageContent->setDefinitions(array(
            DefinitionFactory::create('contentType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Content Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'poster' => 'Poster',
                        'boxCover' => 'Box Cover',
                        'thumbnail' => 'Thumbnail'
                    )
                )
            )),
            DefinitionFactory::create('xResolution', 'Text', array(
                'labels' => array(
                    'en_US' => 'X Resolution'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('yResolution', 'Text', array(
                'labels' => array(
                    'en_US' => 'Y Resolution'
                ),
                'isLocalized' => false
            ))
        ));
        $imageContent->addEntityRelationship('subcontent', 'imageSubcontent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $imageContent->getRelationship('subcontent')->setLabel('Subcontent', 'en_US');

        $subtitleContent = $this->createType('subtitleContent', $content);
        $subtitleContent->setLabel('Subtitle', 'en_US');
        $subtitleContent->setDefinitions(array(
            DefinitionFactory::create('language', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Language'
                ),
                'isLocalized' => false,
                'providerName' => 'language'
            ))
        ));
        $subtitleContent->addEntityRelationship('subcontent', 'subtitleSubcontent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $subtitleContent->getRelationship('subcontent')->setLabel('Subcontent', 'en_US');

        $subcontent = $this->createType('subcontent', $baseEntity);
        $subcontent->setDefinitions(array(
            DefinitionFactory::create('consumerUrl', 'Text', array(
                'labels' => array(
                    'en_US' => 'Consumer URL'
                ),
                'descriptions' => array(
                    'en_US' => 'URL where the consumer user interface should fetch it.'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('contentFileSize', 'Text', array(
                'labels' => array(
                    'en_US' => 'Content File Size'
                ),
                'descriptions' => array(
                    'en_US' => 'Content file size.'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('contentCheckSum', 'Text', array(
                'labels' => array(
                    'en_US' => 'Content MD5 Checksum'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('sourcePath', 'Text', array(
                'labels' => array(
                    'en_US' => 'Source Path'
                ),
                'entityLabel' => true,
                'descriptions' => array(
                    'en_US' => 'URL where file is stored in the system.'
                ),
                'isLocalized' => false
            ))
        ));

        $videoSubcontent = $this->createType('videoSubcontent', $subcontent);
        $videoSubcontent->setDefinitions(array(
            DefinitionFactory::create('resolution', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Resolution'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        '480i' => '480i',
                        '720p' => '720p',
                        '1080i' => '1080i',
                        '1080p' => '1080p'
                    )
                )
            )),
            DefinitionFactory::create('frameRate', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Frame Rate'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        24 => 24,
                        30 => 30,
                        60 => 60
                    )
                )
            )),
            DefinitionFactory::create('codec', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Codec'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'MPEG2' => 'MPEG2',
                        'AVC MP@L30' => 'AVC MP@L30',
                        'AVC MP@L42' => 'AVC MP@L42',
                        'AVC HP@L30' => 'AVC HP@L30',
                        'AVC HP@L42' => 'AVC HP@L42',
                        'MPEG4-MVC' => 'MPEG4-MVC'
                    )
                )
            )),
            DefinitionFactory::create('bitRate', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Bit Rate'
                ),
            )),
            DefinitionFactory::create('isHDContent', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Is HD Content'
                ),
            )),
            DefinitionFactory::create('protectPolicyGroupId', 'Text', array(
                'labels' => array(
                    'en_US' => 'Protect Policy Group ID'
                ),
                'isLocalized' => false,
                'disabled' => true,
                'required' => false,
                'unique' => false
            ))
        ));

        $sourceVideoSub = $this->createType('sourceVideoSub', $videoSubcontent);
        $sourceVideoSub->setLabel('Source Video Subcontent', 'en_US');
        $sourceVideoSub->addEntityRelationship('transSubs', 'transVideoSub', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $sourceVideoSub->getRelationship('transSubs')->setLabel('Transcoded Video Subcontent', 'en_US');
        $sourceVideoSub->addEntityRelationship('protectSubs', 'protectVideoSub', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $sourceVideoSub->getRelationship('protectSubs')->setLabel('Protected Video Subcontent', 'en_US');

        $transVideoSub = $this->createType('transVideoSub', $videoSubcontent);
        $transVideoSub->setLabel('Transcoded Video Subcontent', 'en_US');
        $transVideoSub->addEntityRelationship('transcodeProfiles', 'transcodeProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'transSubs'));
        $transVideoSub->getRelationship('transcodeProfiles')->setLabel('Transcode Profiles', 'en_US');

        $protectVideoSub = $this->createType('protectVideoSub', $videoSubcontent);
        $protectVideoSub->setLabel('Protected Video Subcontent', 'en_US');
        $protectVideoSub->addEntityRelationship('transcodeProfiles', 'transcodeProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'protectSubs'));
        $protectVideoSub->getRelationship('transcodeProfiles')->setLabel('Transcode Profiles', 'en_US');
        $protectVideoSub->addEntityRelationship('protectProfiles', 'protectProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'protectSubs'));
        $protectVideoSub->getRelationship('protectProfiles')->setLabel('Protect Profiles', 'en_US');

        $imageSubcontent = $this->createType('imageSubcontent', $subcontent);
        $imageSubcontent->setLabel('Image Subcontent', 'en_US');
        $imageSubcontent->setDefinitions(array(
            DefinitionFactory::create('xResolution', 'Integer', array(
                'labels' => array(
                    'en_US' => 'X-Resolution'
                ),
                'defaultValue' => 0,
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            )),
            DefinitionFactory::create('yResolution', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Y-Resolution'
                ),
                'defaultValue' => 0,
                'validators' => array(
                    new Validator('GreaterThan', array('min' => 0))
                )
            )),
            DefinitionFactory::create('language', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Language'
                ),
                'isLocalized' => false,
                'providerName' => 'language'
            ))
        ));

        $subtitleSubcontent = $this->createType('subtitleSubcontent', $subcontent);
        $subtitleSubcontent->setLabel('Subtitle Subcontent', 'en_US');

        $offer = $this->createType('offer', $baseMetadata);
        $offer->setLabel('Offer', 'en_US');
        $offer->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $offer->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $offer->setFlag(self::FLAG_CREATE_QA_VIEW);
        $offer->setDefinitions(array(
            DefinitionFactory::create('billingId', 'Text', array(
                'labels' => array(
                    'en_US' => 'Billing ID'
                ),
                'entityLabel' => true,
                'isLocalized' => false,
                'validators' => array(
                    new Validator('Regex', array('pattern' => '/[0-9A-Za-z]{5}/'))
                )
            )),
            DefinitionFactory::create('offertype', 'Text', array(
                'labels' => array(
                    'en_US' => 'Offer Type'
                ),
                'isLocalized' => false
            )),
            DefinitionFactory::create('offerpackageid', 'Text', array(
                'labels' => array(
                    'en_US' => 'Offer Package ID'
                ),
                'isLocalized' => false
            ))
        ));
        $offer->addEntityRelationship('terms', 'term', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'offers'));
        $offer->getRelationship('terms')->setLabel('Terms', 'en_US');
        $offer->addEntityRelationship('programs', 'program', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'offers', 'inverse' => true));
        $offer->getRelationship('programs')->setLabel('Programs', 'en_US');
        $offer->addEntityRelationship('seriess', 'series', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'offers', 'inverse' => true));
        $offer->getRelationship('seriess')->setLabel('Series', 'en_US');
        $offer->addEntityRelationship('brands', 'brand', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'offers', 'inverse' => true));
        $offer->getRelationship('brands')->setLabel('Brand', 'en_US');
        $offer->addEntityRelationship('subscriptionPackages', 'subscriptionPackage', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'offers', 'inverse' => true));
        $offer->getRelationship('subscriptionPackages')->setLabel('Subscription Packages', 'en_US');
        $offer->addEntityRelationship('tvodCollections', 'tvodCollection', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'offers', 'inverse' => true));
        $offer->getRelationship('tvodCollections')->setLabel('Collections', 'en_US');
        $offer->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'offers', 'inverse' => true));
        $offer->getRelationship('events')->setLabel('Events', 'en_US');

        $term = $this->createType('term', $baseMetadata);
        $term->setLabel('Term', 'en_US');
        $term->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $term->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $term->setFlag(self::FLAG_CREATE_QA_VIEW);
        $term->setDefinitions(array(
            DefinitionFactory::create('contractName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Contract Name'
                ),
                'entityLabel' => true,
                'isLocalized' => false
            )),
            DefinitionFactory::create('suggestedPrice', 'Currency', array(
                'labels' => array(
                    'en_US' => 'Suggested Price'
                )
            )),
            DefinitionFactory::create('price', 'Currency', array(
                'labels' => array(
                    'en_US' => 'Price'
                )
            ))
        ));
        $term->addEntityRelationship('offers', 'offer', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'terms', 'inverse' => true));
        $term->getRelationship('offers')->setLabel('Offers', 'en_US');
        $term->addEntityRelationship('termMappings', 'termMapping', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'terms'));
        $term->getRelationship('termMappings')->setLabel('Term Mappings', 'en_US');

        $termMapping = $this->createType('termMapping', $baseEntity);
        $termMapping->setLabel('Term Mapping', 'en_US');
        $termMapping->setDefinitions(array(
            DefinitionFactory::create('contractName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Contract Name'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => true,
                'entityLabel' => true
            )),
            DefinitionFactory::create('policyGroupId', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Policy Group ID'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => false
            )),
            DefinitionFactory::create('policyType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Policy Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => true,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'free' => 'Free',
                        'tvod' => 'TVOD',
                        'svod' => 'SVOD'
                    )
                )
            )),
            DefinitionFactory::create('policyId', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Policy ID'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => false
            )),
            DefinitionFactory::create('contentType', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Content Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'movie' => 'Movie',
                        'preview' => 'Preview',
                        'barker' => 'Barker',
                        'event' => 'Event',
                        'channel' => 'Channel'
                    )
                )
            )),
            DefinitionFactory::create('duration', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Duration'
                )
            ))
        ));
        $termMapping->addEntityRelationship('deviceProfiles', 'deviceProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'termMappings'));
        $termMapping->getRelationship('deviceProfiles')->setLabel('Device Profiles', 'en_US');
        $termMapping->addEntityRelationship('terms', 'term', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'termMappings', 'inverse' => true));
        $termMapping->getRelationship('terms')->setLabel('Terms', 'en_US');
        $termMapping->addEntityRelationship('policyGroupProfiles', 'policyGroupProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'termMappings'));
        $termMapping->getRelationship('policyGroupProfiles')->setLabel('Policy Group Profile', 'en_US');
        $termMapping->addEntityRelationship('policyProfiles', 'policyProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'termMappings'));
        $termMapping->getRelationship('policyProfiles')->setLabel('Policy Profile', 'en_US');

        $channel = $this->createType('channel', $baseMetadataWithContent);
        $channel->setLabel('Channel', 'en_US');
        $channel->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $channel->setFlag(self::FLAG_CREATE_QA_VIEW);
        $channel->setFlag(self::FLAG_CREATE_GENERAL_TASK_VIEW);
        $channel->setFlag(self::FLAG_CREATE_QA_CONTENT_VIEW);
        $channel->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $channel->setDefinitions(array(
            DefinitionFactory::create('channelId', 'ShortText', array(
                'indexed' => true,
                'labels' => array(
                    'en_US' => 'Channel ID'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => true,
            )),
            DefinitionFactory::create('BroadcastServiceId', 'ShortText', array(
                'indexed' => true,
                'labels' => array(
                    'en_US' => 'Broadcast Service ID'
                ),
                'isLocalized' => false,
                'required' => false,
                'unique' => true,
            )),
            DefinitionFactory::create('channelPackager', 'Text', array(
                'labels' => array(
                    'en_US' => 'Channel Packager'
                ),
                'isLocalized' => false,
                'required' => true,
            )),
            DefinitionFactory::create('udpMulticastIP', 'Text', array(
                'labels' => array(
                    'en_US' => 'UDP Multicast IP'
                ),
                'isLocalized' => false,
                'required' => true,
            )),
            DefinitionFactory::create('enabled', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Enabled?'
                )
            )),
            DefinitionFactory::create('allowedOnBrowsers', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Allow on browsers?'
                )
            )),
            DefinitionFactory::create('freeChannel', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Free Channel?'
                )
            )),
            DefinitionFactory::create('HDChannel', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'HD Channel?'
                )
            )),
            DefinitionFactory::create('catchupChannel', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Catchup Channel?'
                ),
                'required' => true,
            )),
            DefinitionFactory::create('displayOrder', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Display Order'
                )
            )),
            DefinitionFactory::create('numberOfAudio', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Number of Audios'
                )
            )),
            DefinitionFactory::create('liveWindowDuration', 'Integer', array(
                'labels' => array(
                    'en_US' => 'Live Window Duration (minutes)'
                )
            ))
        ));
        $channel->addEntityRelationship('channelDays', 'channelDay', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'channels'));
        $channel->getRelationship('channelDays')->setLabel('Daily Schedule Slots', 'en_US');
        $channel->addEntityRelationship('subscriptionPackages', 'subscriptionPackage', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'channels'));
        $channel->getRelationship('subscriptionPackages')->setLabel('Subscription Packages', 'en_US');
        $channel->addEntityRelationship('encodeProfiles', 'encodeProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'channels'));
        $channel->getRelationship('encodeProfiles')->setLabel('Encode Profiles', 'en_US');
        $channel->addEntityRelationship('imageContent', 'imageContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $channel->getRelationship('imageContent')->setLabel('Channel Logo', 'en_US');

        $channelDay = $this->createType('channelDay', $base);
        $channelDay->setLabel('Daily Schedule Slots', 'en_US');
        $channelDay->setDefinitions(array(
            DefinitionFactory::create('date', 'Date', array(
                'indexed' => true,
                'labels' => array(
                    'en_US' => 'Slots Date'
                ),
                'required' => true,
                'isLocalized' => false,
            )),
        ));
        $channelDay->addEntityRelationship('channels', 'channel', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'channelDays', 'inverse' => true));
        $channelDay->getRelationship('channels')->setLabel('Channel', 'en_US');
        $channelDay->addEntityRelationship('scheduleSlots', 'scheduleSlot', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $channelDay->getRelationship('scheduleSlots')->setLabel('Schedule Slots per Day', 'en_US');

        $scheduleSlot = $this->createType('scheduleSlot', $base);
        $scheduleSlot->setLabel('Schedule Slot', 'en_US');
        $scheduleSlot->setDefinitions(array(
            /* start */
            DefinitionFactory::create('linearBroadcastDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Broadcast Start Date'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => false
            )),
            /* stop */
            DefinitionFactory::create('linearBroadcastEndDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Broadcast End Date'
                ),
                'isLocalized' => false,
                'required' => false,
                'unique' => false
            )),
            /* title */
            DefinitionFactory::create('title', 'Text', array(
                'labels' => array(
                    'en_US' => 'Title'
                ),
                'entityLabel' => true,
                'isLocalized' => true,
                'required' => true,
                'unique' => false
            )),
            /* desc */
            DefinitionFactory::create('summary', 'Text', array(
                'formElementName' => Type::ELEMENT_TEXTAREA,
                'labels' => array(
                    'en_US' => 'Summary'
                ),
                'isLocalized' => true,
                'required' => false,
                'unique' => false
            )),

            DefinitionFactory::create('genre', 'Text', array(
                'labels' => array(
                    'en_US' => 'Genre'
                ),
                'isLocalized' => true,
                'required' => false
            )),

            DefinitionFactory::create('rating', 'ShortText', array(
                'labels' => array(
                    'en_US' => 'Rating'
                ),
                'isLocalized' => false,
                'required' => false
            )),

            /* country */
            DefinitionFactory::create('countryOfOrigin', 'MultiSelection', array(
                'labels' => array(
                    'en_US' => 'Country of Origin'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'required' => false,
                'providerName' => 'country' // ISO-3166-1
            )),
            /* sub-title */
            DefinitionFactory::create('episodeName', 'Text', array(
                'labels' => array(
                    'en_US' => 'Episode Name'
                ),
                'isLocalized' => true,
                'required' => false
            )),
            /* episode-num */
            DefinitionFactory::create('episodeId', 'Text', array(
                'labels' => array(
                    'en_US' => 'Episode ID'
                ),
                'isLocalized' => false,
                'required' => false
            )),
            /* video/aspect we'll need to apply a mapping */
            DefinitionFactory::create('screenFormat', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Screen Format'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'Standard' => 'Standard',
                        'Widescreen' => 'Widescreen',
                        'Letterbox' => 'Letterbox',
                        'OAR' => 'OAR'
                    )
                )
            )),
            DefinitionFactory::create('imageUrl', 'Text', array(
                'labels' => array(
                    'en_US' => 'Image URL'
                ),
                'isLocalized' => false,
                'required' => false
            )),
            DefinitionFactory::create('showType', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Show Type'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'Series' => 'Series',
                        'Kids' => 'Kids',
                        'Movies' => 'Movies',
                        'Sports' => 'Sports',
                        'Music' => 'Music',
                        'Events' => 'Events',
                        'Ad' => 'Ad',
                        'Lifestyle' => 'Lifestyle',
                        'Commercial' => 'Commercial',
                        'Documentary' => 'Documentary',
                        'Educational' => 'Educational',
                        'Entertainment' => 'Entertainment',
                        'News' => 'News',
                        'Religious' => 'Religious',
                        'Others' => 'Others'
                    )
                )
            )),
            DefinitionFactory::create('catchUp ', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Catch Up'
                ),
                'flags' => array(
                    EpgWidget::RECIPE_FLAG_IS_ACTION
                )
            )),
             DefinitionFactory::create('STBEnabled ', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'STB Enabled'
                ),
                'flags' => array(
                    EpgWidget::RECIPE_FLAG_IS_ACTION
                )
            )),
            DefinitionFactory::create('Downloadable ', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Downloadable'
                ),
                'flags' => array(
                    EpgWidget::RECIPE_FLAG_IS_ACTION
                )
            )),
            DefinitionFactory::create('blackOut ', 'Boolean', array(
                'labels' => array(
                    'en_US' => 'Black Out'
                ),
                'flags' => array(
                    EpgWidget::RECIPE_FLAG_IS_ACTION
                )
            ))
        ));
        $scheduleSlot->addEntityRelationship('programs', 'program', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'scheduleSlots'));
        $scheduleSlot->getRelationship('programs')->setLabel('Program', 'en_US');
        $scheduleSlot->addEntityRelationship('events', 'event', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'scheduleSlots'));
        $scheduleSlot->getRelationship('events')->setLabel('Events', 'en_US');

        $event = $this->createType('event', $baseMetadataWithContent);
        $event->setLabel('Event', 'en_US');
        $event->setFlag(self::FLAG_CREATE_METADATA_VIEW);
        $event->setFlag(self::FLAG_CREATE_QA_VIEW);
        $event->setFlag(self::FLAG_CREATE_GENERAL_TASK_VIEW);
        $event->setFlag(self::FLAG_CREATE_QA_CONTENT_VIEW);
        $event->setWorkflowDiagram('com.irdeto.jumpstart.workflow.EternalWorkflow', 'entityId');
        $event->setDefinitions(array(
            DefinitionFactory::create('eventBroadcastDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Broadcast Start Date'
                ),
                'isLocalized' => false,
                'required' => true,
                'unique' => false
            )),
            DefinitionFactory::create('eventBroadcastEndDate', 'Date', array(
                'labels' => array(
                    'en_US' => 'Broadcast End Date'
                ),
                'isLocalized' => false,
                'required' => false,
                'unique' => false
            )),
            DefinitionFactory::create('screenFormat', 'Selection', array(
                'labels' => array(
                    'en_US' => 'Screen Format'
                ),
                'internalType' => 'Text',
                'isLocalized' => false,
                'providerName' => 'array',
                'providerOptions' => array(
                    'data' => array(
                        'Standard' => 'Standard',
                        'Widescreen' => 'Widescreen',
                        'Letterbox' => 'Letterbox',
                        'OAR' => 'OAR'
                    )
                )
            ))
        ));
        $event->addEntityRelationship('offers', 'offer', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events'));
        $event->getRelationship('offers')->setLabel('Offers', 'en_US');
        $event->addEntityRelationship('genres', 'genre', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events', 'inverse' => true));
        $event->getRelationship('genres')->setLabel('Genres', 'en_US');
        $event->addEntityRelationship('ratings', 'rating', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events'));
        $event->getRelationship('ratings')->setLabel('Ratings', 'en_US');
        $event->addEntityRelationship('programs', 'program', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events', 'inverse' => true));
        $event->getRelationship('programs')->setLabel('Programs', 'en_US');
        $event->addEntityRelationship('encodeProfiles', 'encodeProfile', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events'));
        $event->getRelationship('encodeProfiles')->setLabel('Encode Profiles', 'en_US');
        $event->addEntityRelationship('scheduleSlots', 'scheduleSlot', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events', 'inverse' => true));
        $event->getRelationship('scheduleSlots')->setLabel('Schedule Slots', 'en_US');
        $event->addEntityRelationship('subscriptionPackages', 'subscriptionPackage', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events'));
        $event->getRelationship('subscriptionPackages')->setLabel('Subscription Packages', 'en_US');
        $event->addEntityRelationship('tvodCollections', 'tvodCollection', Relationship::MAPPING_MANY_MANY, array(), array('inverseKey' => 'events'));
        $event->getRelationship('tvodCollections')->setLabel('Collections', 'en_US');
        $event->addEntityRelationship('imageContent', 'imageContent', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $event->getRelationship('imageContent')->setLabel('Images', 'en_US');
        $event->addEntityRelationship('contributors', 'person', Relationship::MAPPING_MANY, array(Relationship::VERSIONED, Relationship::EMBEDDED));
        $event->getRelationship('contributors')->setLabel('Contributors', 'en_US');

        return array(
            'baseEntity' => $baseEntity,
            'base' => $base,
            'baseMetadata' => $baseMetadata,
            'baseMetadataWithTitle' => $baseMetadataWithTitle,
            'baseMetadataWithContent' => $baseMetadataWithContent,
            'genre' => $genre,
            'program' => $program,
            'series' => $series,
            'brand' => $brand,
            'channel' => $channel,
            'scheduleSlot' => $scheduleSlot,
            'channelDay' => $channelDay,
            'event' => $event,
            'subscriptionPackage' => $subscriptionPackage,
            'tvodCollection' => $tvodCollection,
            'offer' => $offer,
            'term' => $term,
            'person' => $person,
            'content' => $content,
            'videoContent' => $videoContent,
            'imageContent' => $imageContent,
            'subcontent' => $subcontent,
            'subtitleContent' => $subtitleContent,
            'subtitleSubcontent' => $subtitleSubcontent,
            'videoSubcontent' => $videoSubcontent,
            'sourceVideoSub' => $sourceVideoSub,
            'transVideoSub' => $transVideoSub,
            'protectVideoSub' => $protectVideoSub,
            'imageSubcontent' => $imageSubcontent,
            'termMapping' => $termMapping,
            'policyGroupProfile' => $policyGroupProfile,
            'policyProfile' => $policyProfile,
            'deviceProfile' => $deviceProfile,
            'baseProtection' => $baseProtection,
            'encodeProfile' => $encodeProfile,
            'transcodeProfile' => $transcodeProfile,
            'protectProfile' => $protectProfile,
            'ratingScheme' => $ratingScheme,
            'rating' => $rating
        );
    }
}
