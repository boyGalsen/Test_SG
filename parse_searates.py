import json
from collections import namedtuple


def mapRoute(route,mappedLocation):
    mapped={}

    for k,v in route.items():
        if ('predictive_eta' in v):
            eta= v['predictive_eta']
        else :
            eta= None
        if(eta is None) :
            eta=None
        #v['date'][0:10]+'T'+v['date'][11:20]+'Z'
        mapped[k]={'location':mappedLocation[v['location']],'date':v['date'][0:10]+'T'+v['date'][11:20]+'Z','actual':v['actual'],'eta': eta}
        print(k)
    return mapped

def mapVessel(vessel):
    data={}
    #mapping = {'locationName':'name','UNLocationCode':'locode','latitude':'lat','longitude':'lng','address.name':'name','address.stateRegion':'state','country':'country'}
    mapping = {'vesselIMONumber':'imo','name':'name','callSign':'call_sign','vesselMMSINumber':'mmsi','flag':'flag'}

    for k,v in mapping.items():
        data[k]=vessel[v]
    return (vessel['id'],data)

def mapContainer(container):
    data={}
    #mapping = {'locationName':'name','UNLocationCode':'locode','latitude':'lat','longitude':'lng','address.name':'name','address.stateRegion':'state','country':'country'}
    mapping = {'containerId':'number','isoCode':'iso_code','sizeType':'size_type','status':'status'}
    for k,v in mapping.items():
        data[k]=container[v]
        data['containerOwner']=container['number'][0:3]
        data['events']=list(map(mapEvent,container['events']))

    return data


def mapEvent(event):
    data={}
    #mapping = {'locationName':'name','UNLocationCode':'locode','latitude':'lat','longitude':'lng','address.name':'name','address.stateRegion':'state','country':'country'}
    #'ISOEquipmentCode':'size_type'
    mapping = {'eventType':'event_type','equipmentEventTypeCode':'event_code','status':'status'}
    #mapping = {'eventType':'event_type','eventDateTime':'date','equipmentEventTypeCode':'event_code','status':'status'}
    for k,v in mapping.items():
        data[k]=event[v]
        data['transportCall']={}
    data['eventDateTime']=event['date'][0:10]+'T'+event['date'][11:20]+'Z'
    return data

def mapLocation(location):
    data={}
    #mapping = {'locationName':'name','UNLocationCode':'locode','latitude':'lat','longitude':'lng','address.name':'name','address.stateRegion':'state','country':'country'}
    mapping = {'locationName':'name','UNLocationCode':'locode','latitude':'lat','longitude':'lng'}
    address={"name":location['name'],"country":location['country'],"stateRegion":location['state']}
    for k,v in mapping.items():
        data[k]=location[v]
    data['address']=address
    return (location['id'],data)

def customStudentDecoder(studentDict):
    return namedtuple('X', studentDict.keys())(*studentDict.values())


def parse(apiResponseData):

    #       Parse JSON into an object with attributes corresponding to dict keys.
    #student = json.loads(apiResponseData, object_hook=customStudentDecoder)
    response = {}
    student= json.loads(apiResponseData)
    metadata=student['data']['metadata']
    locations=student['data']['locations']
    containers=student['data']['containers']
    vessels=student['data']['vessels']
    facilities=student['data']['facilities']
    route=student['data']['route']
    route_data=student['data']['route_data']

    mappedLocation=dict(map(mapLocation,locations))
    response["locations"]=list(mappedLocation.values())
    response["searchId"] = metadata['number']
    response["searchType"]=metadata['type']

    response['currentLocation']={'latitude': route_data['pin'][0],'longitude':route_data['pin'][1]}
    mappedRoute=mapRoute(route,mappedLocation)
    response['route']=mappedRoute
    mappedVessel=dict(map(mapVessel,vessels))
    response['vessels']=list(mappedVessel.values())

    #mappedRoute=dict(map(mapRoute,route,mappedLocation))
    response['isTranshipment']=len(mappedVessel.keys())>1
    response['raw']={"jsonValue":apiResponseData}
    #mapContaines=list(map(mapContainer,containers))
    response['containers'] = list(map(mapContainer,containers))


    type=metadata['type']
    if(type=='CT') :
        response["containerNumber"]=metadata['number']
    elif (type=='BL'):
        response["billOffLadingNumber"]=metadata['number']
    elif (type=='BK'):
        response["bookingNumber"]=metadata['number']
    response["status"]= metadata['status']
    response["sealine"]=metadata['sealine']
    response["sealineName"]=metadata['sealine_name']
    response["snapshotTimestamp"]=metadata['updated_at'][0:10]+'T'+metadata['updated_at'][11:20]+'Z'
    return json.dumps(response,indent=4)
